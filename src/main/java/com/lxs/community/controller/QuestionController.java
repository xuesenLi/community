package com.lxs.community.controller;

import com.lxs.community.dto.QuestionDTO;
import com.lxs.community.mapper.QuestionMapper;
import com.lxs.community.model.Question;
import com.lxs.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Mr.Li
 * @date 2019/8/9 - 21:46
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){

        QuestionDTO questionDTO = questionService.getById(id);

        model.addAttribute("question", questionDTO);
        return "question";
    }
}
