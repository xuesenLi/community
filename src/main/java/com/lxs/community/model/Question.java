package com.lxs.community.model;

import lombok.Data;

/**
 * @author Mr.Li
 * @date 2019/8/8 - 16:27
 */
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer viewCount;
    private Integer creator;
    private Integer commentCount;
    private Integer likeCount;

}
