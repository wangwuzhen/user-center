package com.ice.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ice.usercenter.common.BaseResponse;
import com.ice.usercenter.common.ErrorCode;
import com.ice.usercenter.common.ResultUtils;
import com.ice.usercenter.execption.BusinessExecption;
import com.ice.usercenter.model.User;
import com.ice.usercenter.model.request.UserLoginRequest;
import com.ice.usercenter.model.request.UserRegisterRequest;
import com.ice.usercenter.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ice.usercenter.constant.UserContant.ADMIN_ROLE;
import static com.ice.usercenter.constant.UserContant.USER_LOGIN_STATE;

@Api(tags = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @ApiOperation(value = "注册接口")
    @PostMapping("/register")
    public BaseResponse<Long>  RegisterUser(@RequestBody UserRegisterRequest registerRequest) {
        String userAccount = registerRequest.getUserAccount();
        String userPassWord = registerRequest.getUserPassWord();
        String checkPassword = registerRequest.getCheckPassWord();
        String planetCode = registerRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassWord, checkPassword, planetCode)) {
           throw new BusinessExecption(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.userRegister(userAccount, userPassWord, checkPassword, planetCode);
        return ResultUtils.success(result);


    }
    @ApiOperation(value = "登录接口")
    @PostMapping("/login")
    public BaseResponse<User> LoginUser(@RequestBody UserLoginRequest loginRequest, HttpServletRequest request) {
        String userAccount = loginRequest.getUserAccount();
        String userPassWord = loginRequest.getUserPassWord();

        if (StringUtils.isAnyBlank(userAccount, userPassWord)) {
            throw new BusinessExecption(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassWord, request);
        return ResultUtils.success(user);

    }
    @ApiOperation(value = "退出登录")
    @PostMapping("/outLogin")
    public BaseResponse<Integer> OutUserLogin(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessExecption(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.outUserLogin(request);
        return ResultUtils.success(result);

    }

    @ApiOperation(value = "用户登录信息")
    @GetMapping("/current")
    public BaseResponse<User> currentUser(HttpServletRequest request) {
        Object userstate = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userstate;
        if (currentUser == null) {
            throw new BusinessExecption(ErrorCode.NO_AUTH);
        }


        User user = userService.getById(currentUser.getId());

        User safeuser = userService.getsafeUser(user);
        return ResultUtils.success(safeuser);

    }

    @ApiOperation(value = "所有用户信息查询")
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {

        if (!isAdmin(request)) {
          throw new BusinessExecption(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        /**
         * userService.list(queryWrapper)   存在bug 会返回所有信息返回前端 包括密码  需要过滤
         */
        List<User> userList = userService.list(queryWrapper);
        //遍历进行脱敏
        List<User> collect = userList.stream().map(user -> userService.getsafeUser(user)).collect(Collectors.toList());
        return ResultUtils.success(collect);

    }
    @ApiOperation(value = "根据id删除用户信息")
    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteUser(@PathVariable("id") long id, HttpServletRequest request) {

        if (!isAdmin(request)) {
            throw new BusinessExecption(ErrorCode.PARAMS_ERROR);
        }

        if (id < 0) {
            throw new BusinessExecption(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.removeById(id);
        return ResultUtils.success(result);

    }

    /**
     * 是否是管理员
     *
     * @param request
     * @return
     */
    public boolean isAdmin(HttpServletRequest request) {
        /**
         * 获取登录态（登录成功后保存到session的用户信息）
         */
        Object user = request.getSession().getAttribute(USER_LOGIN_STATE);
        User usersafe = (User) user;
        /**
         * 是否是管理员  管理员才可进行查询或者删除用户信息
         */
        //非管理员操作 返回空数组
        return usersafe != null && usersafe.getUserRole() == ADMIN_ROLE;
    }


}
