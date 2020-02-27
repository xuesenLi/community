package com.lxs.community.dto;

import lombok.Data;

/**
 * @author Mr.Li
 * @date 2019/8/6 - 20:50
 * 获取github授权后的用户信息
 */
@Data
public class GithubUser {
    private String name;

    private Long id;

    private String bio;  //描述

    //获取github头像路径
    private String avatar_url;

}
