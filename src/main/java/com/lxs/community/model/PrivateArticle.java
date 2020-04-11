package com.lxs.community.model;

import lombok.Data;

import java.util.Date;

@Data
public class PrivateArticle {
    private Integer id;

    private String title;

    private String description;

    private Integer creator;

    private String tag;

    private Date gmtCreate;

    private Date gmtGmtModified;

}
