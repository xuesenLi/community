package com.lxs.community.controller;

import com.lxs.community.dto.CommentDTO;
import com.lxs.community.dto.QuestionDTO;
import com.lxs.community.enums.CommentTypeEnum;
import com.lxs.community.mapper.QuestionMapper;
import com.lxs.community.model.Question;
import com.lxs.community.service.CommentService;
import com.lxs.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Mr.Li
 * @date 2019/8/9 - 21:46
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){

        QuestionDTO questionDTO = questionService.getById(id);

        //查找相似标签
        List<QuestionDTO> relatedQuestions = questionService.selectQuestionByTags(questionDTO);

        //显示评论区
        List<CommentDTO> comments =  commentService.ListByQuestionId(id, CommentTypeEnum.QUESTION.getType());

        //通过 Find Usages查看该方法在哪里调用过。
        //累加阅读数， 不能够在questionService.getById(id)这个里面去写，其他操作也调用了getById方法。。
        questionService.incView(id);
        questionDTO.setViewCount(questionDTO.getViewCount()+1);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);

        return "question";
    }
}
