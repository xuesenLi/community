package com.lxs.community.controller;

import com.lxs.community.dto.ResultDTO;
import com.lxs.community.exception.CustomizeErrorCode;
import com.lxs.community.model.Comment;
import com.lxs.community.model.User;
import com.lxs.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Li
 * @date 2019/8/10 - 16:21
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody Comment comment,
                       HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());

        commentService.insert(comment);

        return ResultDTO.OkOf();
    }

}
