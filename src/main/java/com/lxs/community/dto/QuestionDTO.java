package com.lxs.community.dto;

import com.lxs.community.model.User;
import lombok.Data;

/**
 * @author Mr.Li
 * @date 2019/8/9 - 11:09
 * 分页查找返回的DTO
 */
@Data
public class QuestionDTO {

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

    private User user;
}
