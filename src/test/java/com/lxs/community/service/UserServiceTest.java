package com.lxs.community.service;

import com.lxs.community.CommunityApplicationTests;
import com.lxs.community.mapper.UserMapper;
import com.lxs.community.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Mr.Li
 * @date 2020/3/4 - 15:40
 */
@Slf4j
public class UserServiceTest extends CommunityApplicationTests {

    @Autowired
    private UserMapper userMapper;


 /*   @Test
    public void selectByToken(){
        String token = "9c0d242f-b809-4c49-b12d-c2a978bf1f07";
        User user = userMapper.selectByToken(token);
        log.info("user = {}", user);

    }*/
}
