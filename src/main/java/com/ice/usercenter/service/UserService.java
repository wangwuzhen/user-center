package com.ice.usercenter.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ice.usercenter.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author HONOR
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-09-17 16:00:15
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return planetCode
     */
    long userRegister(String userAccount,String userPassword,String checkPassword,String planetCode);

    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    List<User> searchAllUser();

    /**
     * 用户脱敏
     * @param orginUser
     * @return
     */
    User getsafeUser(User orginUser);

    /**
     * 注销用户登录
     * @param request
     * @return
     */
    int outUserLogin(HttpServletRequest request);
}
