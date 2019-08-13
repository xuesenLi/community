package com.lxs.community.dto;

import lombok.Data;

/**
 * @author Mr.Li
 * @date 2019/8/13 - 10:50
 */
@Data
public class NotificationDTO {

    private Integer id;  //通知id
    private Integer notifier;   //通知人
    private Integer receiver;   //接受者
    private Integer outerid;    //判断是回复评论， 点赞， 还是回答了你的问题
    private Integer type;      //判断是回复评论， 点赞， 还是回答了你的问题
    private Long gmtCreate;
    private Integer status;   // 状态 已读 或未读
    private String notifierName;
    private String outerTitle;

    private String typeName;    //标记 type所对应的值


}
