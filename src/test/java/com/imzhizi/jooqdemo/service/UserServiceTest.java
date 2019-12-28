package com.imzhizi.jooqdemo.service;

import com.imzhizi.jooqdemo.domain.User;
import com.imzhizi.jooqdemo.tables.records.UserRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper mapper;

    @Test
    public void getUserRecordById() {
        UserRecord userRecord = userService.getUserRecordById(1);
        System.out.println(userRecord);
    }

    @Test
    public void createUser() {
        User user = new User();
        user.setUsername("user1");
        user.setPwd("userpwd");
        userService.createUser(user);
    }
}