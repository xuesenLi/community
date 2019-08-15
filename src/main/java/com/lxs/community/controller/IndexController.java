package com.lxs.community.controller;

import com.lxs.community.cache.HotTagCache;
import com.lxs.community.dto.HotTagDTO;
import com.lxs.community.dto.PaginationDTO;
import com.lxs.community.dto.QuestionDTO;
import com.lxs.community.mapper.QuestionMapper;
import com.lxs.community.mapper.UserMapper;
import com.lxs.community.model.Question;
import com.lxs.community.model.User;
import com.lxs.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Mr.Li
 * @date 2019/8/6 - 16:35
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    //定时器
    @Autowired
    private HotTagCache hotTagCache;


    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "8") Integer size,
                        @RequestParam(value = "search", required = false) String search,
                        @RequestParam(value = "tag", required = false) String tag){

        //展示页表信息
        PaginationDTO paginationDTO = questionService.list(search, tag, page, size);

        List<HotTagDTO> hotTags = hotTagCache.getHots();

        model.addAttribute("pagination", paginationDTO);

        //回显的作用
        model.addAttribute("search", search);
        model.addAttribute("tag", tag);

        model.addAttribute("hotTags", hotTags);
        return "index";
    }

}
