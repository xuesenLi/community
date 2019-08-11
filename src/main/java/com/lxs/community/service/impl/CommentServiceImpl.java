package com.lxs.community.service.impl;

import com.lxs.community.dto.CommentDTO;
import com.lxs.community.enums.CommentTypeEnum;
import com.lxs.community.exception.CustomizeErrorCode;
import com.lxs.community.exception.CustomizeException;
import com.lxs.community.mapper.CommentMapper;
import com.lxs.community.mapper.QuestionMapper;
import com.lxs.community.mapper.UserMapper;
import com.lxs.community.model.Comment;
import com.lxs.community.model.Question;
import com.lxs.community.model.User;
import com.lxs.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Mr.Li
 * @date 2019/8/10 - 17:14
 */
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    //添加事务
    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复品论
            Comment dbComment = commentMapper.selectById(comment);
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            commentMapper.insert(comment);

        }else{
            //回复问题
            Question question = questionMapper.getById(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);
            //回复成功后需要在question表 comment_count ++
            questionMapper.updateCommentCount(question.getId());

        }




    }

    @Override
    public List<CommentDTO> ListByQuestionId(Integer id) {
        //查找回复的问题 QUESTION（1）
        List<Comment> comments = commentMapper.selectByParentId(id, CommentTypeEnum.QUESTION.getType());

        //返回 CommentDTO 的user 对象
        if(comments.size() == 0){
            return new ArrayList<>();
        }

        //stream()方法和collect()方法都是java8的新特性:
        // 获取评论人    ：   返回comments 中所有的 commentators 不重复
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        //set - >>   List
        List<Integer> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取品论人并转换为map
        List<User> users = new ArrayList<>();
        for (Integer userId : userIds) {
            User user = userMapper.findById(userId);
            users.add(user);
        }
        //将users 分装为map
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换 comment -> commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));

            return commentDTO;
        }).collect(Collectors.toList());


        //查找回复的评论    COMMENT(2);
        //
        return commentDTOS;
    }


}
