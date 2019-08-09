package com.lxs.community.model;

import lombok.Data;

/**
 * @author Mr.Li
 * @date 2019/8/8 - 14:15
 */
@Data  //自动生成getter setter
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String bio;
    private String avatarUrl;  //保存头像路径地址

}
