package com.lxs.community.model;

import lombok.Data;

import java.util.Date;

@Data
public class Follow {
    private Integer id;

    private Integer uId;

    private Integer fId;

    private Date gmtCreate;

    private Date gmtModified;

}
