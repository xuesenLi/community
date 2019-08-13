package com.lxs.community.dto;

import com.lxs.community.model.User;
import lombok.Data;

/**
 * @author Mr.Li
 * @date 2019/8/11 - 20:42
 */
@Data
public class CommentDTO {
    private Integer id;
    private Integer parentId; //父类Id
    private Integer type;  //父类类型
    private Integer commentator;  //评论人Id
    private Long gmtCreate;     //创建时间
    private Long gmtModified;   //更行时间
    private Integer likeCount;  //点赞数

    private String content;

    private Integer commentCount;  //子评论个数

    private User user;

}
