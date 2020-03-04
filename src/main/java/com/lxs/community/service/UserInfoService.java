package com.lxs.community.service;

import com.lxs.community.dto.ResponseVO;
import com.lxs.community.pojo.UserInfo;

/**
 * @author Mr.Li
 * @date 2020/2/29 - 16:46
 */
public interface UserInfoService {

    /**
     * 登录
     *
     * @param email
     * @param password
     * @return
     */
    ResponseVO<UserInfo> login(String email, String password);

    /**
     * 注册
     *
     * @param userInfo
     * @return
     */
    ResponseVO<UserInfo> register(UserInfo userInfo);

}
