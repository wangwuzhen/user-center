package com.ice.usercenter.service.impl;

import com.ice.usercenter.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    public void  register(){

        String userAccount="iced";
        String userPassword="";
        String checkPassword="12345678";
        String planetCode="";
        long result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assert.assertEquals(-1, result);
        userAccount="ice";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assert.assertEquals(-1, result);
        userPassword="123456";
        checkPassword="123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assert.assertEquals(-1, result);
        userAccount="1234";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assert.assertEquals(-1, result);
        userAccount="ice dd";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assert.assertEquals(-1, result);
        userAccount="icedd";
        userPassword="12345678";
        checkPassword="12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assert.assertTrue(result>0);
    }


}