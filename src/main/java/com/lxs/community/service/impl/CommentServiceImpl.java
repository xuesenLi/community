package com.lxs.community.service.impl;

import com.lxs.community.dto.CommentDTO;
import com.lxs.community.enums.CommentTypeEnum;
import com.lxs.community.enums.NotificationStatusEnum;
import com.lxs.community.enums.NotificationTypeEnum;
import com.lxs.community.exception.CustomizeErrorCode;
import com.lxs.community.exception.CustomizeException;
import com.lxs.community.mapper.CommentMapper;
import com.lxs.community.mapper.NotificationMapper;
import com.lxs.community.mapper.QuestionMapper;
import com.lxs.community.mapper.UserMapper;
import com.lxs.community.model.Comment;
import com.lxs.community.model.Notification;
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

    @Autowired
    private NotificationMapper notificationMapper;



    //添加事务
    @Transactional
    public void insert(Comment comment, User commentator) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            //回复问题
            Question question = questionMapper.getById(dbComment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);
            //评论成功后 需要在comment表中，comment_count ++
            commentMapper.incCommentCount(comment.getParentId());

            //展示通知
            createNotification(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());

        }else{
            //回复问题
            Question question = questionMapper.getById(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);
            //回复成功后需要在question表 comment_count ++
            questionMapper.updateCommentCount(question.getId());

            //展示通知
            createNotification(comment, question.getCreator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());

        }




    }

    //展示通知
    private void createNotification(Comment comment, Integer receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationTYpe, Integer outerId) {

        //如果自己给自己回复 或者评论就不通知
        /*if(receiver == comment.getCommentator()){
            return;
        }*/
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationTYpe.getType());

        //发送者  回复 接受者 那一条 comment.content 或则 question.title
        //回复的commentId, 或则 question.Id
        notification.setOuterid(outerId);
        //发送者
        notification.setNotifier(comment.getCommentator());
        //接受者
        notification.setReceiver(receiver);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());

        //发送者name
        notification.setNotifierName(notifierName);
        //回复的那条内容
        notification.setOuterTitle(outerTitle);

        notificationMapper.insert(notification);
    }

    @Override
    public List<CommentDTO> ListByQuestionId(Integer id, Integer type) {
        //查找回复的问题 QUESTION（1）
        List<Comment> comments = commentMapper.selectByParentId(id, type);

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
