package com.lxs.community.model;

import lombok.Data;

import java.util.Date;

/**
 * @author Mr.Li
 * @date 2019/8/8 - 14:15
 */
@Data  //自动生成getter setter
public class User {

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
