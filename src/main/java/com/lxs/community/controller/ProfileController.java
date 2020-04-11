package com.lxs.community.controller;

import com.lxs.community.dto.PaginationDTO;
import com.lxs.community.enums.ProfileActionEnum;
import com.lxs.community.model.Follow;
import com.lxs.community.model.PrivateArticle;
import com.lxs.community.model.User;
import com.lxs.community.service.FollowService;
import com.lxs.community.service.NotificationService;
import com.lxs.community.service.PrivateArticleService;
import com.lxs.community.service.QuestionService;
import com.lxs.community.utils.GlobalConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Mr.Li
 * @date 2019/8/9 - 15:05
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private FollowService followService;

    @Autowired
    private PrivateArticleService privateArticleService;


    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model, HttpSession session,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "15") Integer size) {

        User user = (User) session.getAttribute(GlobalConst.CURRENT_USER);
        if (user == null) {
            return "redirect:/";
        }

        ProfileActionEnum currentEnum = ProfileActionEnum.getEnumByName(action);
        PaginationDTO paginationDTO = new PaginationDTO<>();

        switch (currentEnum){
            case QUESTIONS:
                paginationDTO = questionService.list(user.getId(), page, size);
                break;
            case REPLIES:
                paginationDTO = notificationService.list(user.getId(), page, size);
                break;
            case PRIVATE_ARTICLE:
                paginationDTO = privateArticleService.list(user.getId(), page, size);
                break;
            case FAN_LIST:
                paginationDTO = followService.getFansList(user.getId(), page, size);
                break;
            case FOLLOW_LIST:
                paginationDTO = followService.getFollowList(user.getId(), page, size);
                break;
        }
        model.addAttribute("pagination", paginationDTO);
        model.addAttribute("section", currentEnum.getName());
        model.addAttribute("sectionName", currentEnum.getDesc());

        return "profile";
    }
}
