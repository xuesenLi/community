package com.lxs.community.dto;

import lombok.Data;

/**
 * @author Mr.Li
 * @date 2019/8/6 - 20:50
 */
@Data
public class GithubUser {
    private String name;

    private Long id;

    private String bio;

    //获取github头像路径
    private String avatar_url;

}
