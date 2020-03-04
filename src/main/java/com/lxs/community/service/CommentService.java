package com.lxs.community.service;

import com.lxs.community.dto.CommentDTO;
import com.lxs.community.model.Comment;
import com.lxs.community.model.User;

import java.util.List;

/**
 * @author Mr.Li
 * @date 2019/8/10 - 16:26
 */

public interface CommentService {

    void insert(Comment comment, User user);

    /**
     * 查找该问题下面的所有评论
     *
     * @param id
     * @param type
     * @return
     */
    List<CommentDTO> ListByQuestionId(Integer id, Integer type);
}
