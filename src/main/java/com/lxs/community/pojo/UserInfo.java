package com.lxs.community.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
    private Integer id;

    private String accountId;

    private String name;

    private String email;

    private String password;

    private String token;

    private String bio;

    private String avatarUrl;

    private Date gmtCreate;

    private Date gmtModified;

}
