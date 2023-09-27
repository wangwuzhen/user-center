package com.ice.usercenter;

import com.ice.usercenter.mapper.UserMapper;
import com.ice.usercenter.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class UserCenterApplicationTests {


    @Resource
   private UserMapper userMapper;


    @Test
    void contextLoads() {

        System.out.println("------------selectList---------------");
        List<User> users = userMapper.selectList(null);
        Assert.assertEquals(5,users.size());
        users.forEach(System.out::println);
//        for(User user:users) {
//            System.out.println(user);
//        }
    }

}
