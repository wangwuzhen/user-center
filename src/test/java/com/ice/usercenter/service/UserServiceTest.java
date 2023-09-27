package com.ice.usercenter.service;
import java.util.Date;

import com.ice.usercenter.mapper.UserMapper;
import com.ice.usercenter.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

   @Resource
  private  UserService userService;

    @Test
    public void testAddUser(){
        User user= new User();
        user.setUsername("iceWang");
        user.setUserAccount("123");
        user.setAvatarUrl("https://images.zsxq.com/Fk6t7wkIMiHgS-jAZSxKwC7YVQDC?e=1698767999&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:bzHsZJkvpSv_VkxizNxcAPRRa8A=");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);


        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assert.assertTrue(result);

    }

}