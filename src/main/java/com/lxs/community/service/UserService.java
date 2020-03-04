package com.lxs.community.service;

import com.lxs.community.dto.ResponseVO;
import com.lxs.community.model.User;


/**
 * @author Mr.Li
 * @date 2019/8/9 - 22:37
 */

public interface UserService {
    /**
     * 修改或者更新github账号。
     *
     * @param user
     */
    void createOrUpdate(User user);

    /**
     * 登录
     *
     * @param email
     * @param password
     * @return
     */
    ResponseVO<User> login(String email, String password);

    /**
     * 注册
     *
     * @param user
     * @return
     */
    ResponseVO<User> register(User user);

}
