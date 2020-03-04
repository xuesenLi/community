package com.lxs.community.model;

import lombok.Data;

/**
 * @author Mr.Li
 * @date 2019/8/10 - 16:15
 * <p>
 * 评论功能主要分为 一级评论 和 二级评论
 */
@Data
public class Comment {

    private Integer id;
    private Integer parentId; //父类Id
    private Integer type;  //父类类型
    private Integer commentator;  //评论人Id
    private Long gmtCreate;     //创建时间
    private Long gmtModified;   //更行时间
    private Integer likeCount;  //点赞数

    private Integer commentCount; //子评论个数
    private String content;

}
