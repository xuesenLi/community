package com.lxs.community.service;

import com.lxs.community.mapper.UserMapper;
import com.lxs.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Li
 * @date 2019/8/9 - 22:37
 */

public interface UserService {
       void createOrUpdate(User user);
}
