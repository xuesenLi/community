package com.lxs.community.dto;

import com.lxs.community.model.User;
import lombok.Data;

import java.util.Date;

/**
 * @author Mr.Li
 * @date 2020/3/30 - 15:32
 */
@Data
public class PrivateArticleDTO {
    private Integer id;

    private String title;

    private Integer creator;

    private String tag;

    private Date gmtCreate;

    private Date gmtGmtModified;

    private String description;

    private User user;
}
