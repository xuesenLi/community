package com.lxs.community.service;

import com.lxs.community.dto.CommentDTO;
import com.lxs.community.mapper.CommentMapper;
import com.lxs.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mr.Li
 * @date 2019/8/10 - 16:26
 */

public interface CommentService {

    void insert(Comment comment);

    /**
     * 查找该问题下面的所有评论
     * @param id
     * @return
     */
    List<CommentDTO> ListByQuestionId(Integer id);
}
