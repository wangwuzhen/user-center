package com.ice.usercenter.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ice.usercenter.common.ErrorCode;
import com.ice.usercenter.execption.BusinessExecption;
import com.ice.usercenter.mapper.UserMapper;
import com.ice.usercenter.model.User;
import com.ice.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ice.usercenter.constant.UserContant.USER_LOGIN_STATE;


/**
 * @author ice
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2023-09-17 16:00:15
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    /**
     * 盐值
     */
    private static final String SALT = "ice";


    @Resource
    private UserMapper userMapper;


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
           throw  new BusinessExecption(ErrorCode.PARAMS_ERROR,"参数为空！");
        }
        if (userAccount.length() < 4) {
            throw  new BusinessExecption(ErrorCode.PARAMS_ERROR,"参数为空！");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw  new BusinessExecption(ErrorCode.PARAMS_ERROR,"用户密码过短！");
        }
        if (planetCode.length() > 5 || planetCode.length() < 0) {
            throw  new BusinessExecption(ErrorCode.PARAMS_ERROR,"星球账号过长！");

        }


        //账户不能包括特殊字符
        String validPattern = "[ _`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw  new BusinessExecption(ErrorCode.PARAMS_ERROR);
        }
        if (!userPassword.equals(checkPassword)) {
            throw  new BusinessExecption(ErrorCode.PARAMS_ERROR,"两次输入密码一致！");
        }
        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw  new BusinessExecption(ErrorCode.PARAMS_ERROR,"账户不能重复！");
        }
        //星球编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode", planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw  new BusinessExecption(ErrorCode.PARAMS_ERROR,"星球编号不能重复！");
        }

        //密码加密

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //添加用户
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean result = this.save(user);
        if (!result) {
            throw  new BusinessExecption(ErrorCode.PARAMS_ERROR,"添加用户失败！");
        }
        return user.getId();

    }

    /**
     * 用户登录功能
     *
     * @param userAccount
     * @param userPassword
     * @return
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw  new BusinessExecption(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if (userAccount.length() < 4) {
            throw  new BusinessExecption(ErrorCode.PARAMS_ERROR,"账户小于4位");
        }
        if (userPassword.length() < 8) {
            throw  new BusinessExecption(ErrorCode.PARAMS_ERROR,"密码小于8位");
        }
        //账户不能包括特殊字符
        String validPattern = "[ _`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw  new BusinessExecption(ErrorCode.PARAMS_ERROR,"匹配存在特殊字符");
        }
        //2.加密

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("User login failed,userAccount cannot match userPassword");
            throw  new BusinessExecption(ErrorCode.PARAMS_ERROR,"账户或密码输入错误！");
        }

        User safeUser = getsafeUser(user);
        //4.记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safeUser);

        //5.返回脱敏后的信息
        return safeUser;
    }

    /**
     * 查询所有用户信息
     *
     * @return
     */
    @Override
    public List<User> searchAllUser() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(null);
        return userMapper.selectList(queryWrapper);

    }

    /**
     * 用户信息脱敏
     *
     * @param orginUser
     * @return
     */
    @Override
    public User getsafeUser(User orginUser) {
        if (orginUser == null) {
            throw  new BusinessExecption(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        //3.用户脱敏
        User safeUser = new User();
        safeUser.setId(orginUser.getId());
        safeUser.setUsername(orginUser.getUsername());
        safeUser.setUserAccount(orginUser.getUserAccount());
        safeUser.setAvatarUrl(orginUser.getAvatarUrl());
        safeUser.setGender(orginUser.getGender());
        safeUser.setPhone(orginUser.getPhone());
        safeUser.setEmail(orginUser.getEmail());
        safeUser.setPlanetCode(orginUser.getPlanetCode());
        safeUser.setUserStatus(orginUser.getUserStatus());
        safeUser.setUserRole(orginUser.getUserRole());
        safeUser.setCreateTime(orginUser.getCreateTime());
        return safeUser;
    }

    @Override
    public int outUserLogin(HttpServletRequest request) {

        request.getSession().removeAttribute(USER_LOGIN_STATE);

        return 1;
    }


}




