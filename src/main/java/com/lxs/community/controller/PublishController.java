package com.lxs.community.controller;

import com.lxs.community.cache.TagCache;
import com.lxs.community.dto.QuestionDTO;
import com.lxs.community.enums.ArticleTypeEnum;
import com.lxs.community.enums.ResponseEnum;
import com.lxs.community.exception.ReturnViewException;
import com.lxs.community.model.Question;
import com.lxs.community.model.User;
import com.lxs.community.service.QuestionService;
import com.lxs.community.utils.GlobalConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Mr.Li
 * @date 2019/8/8 - 15:41
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       HttpSession session,
                       Model model) {
        User user = (User) session.getAttribute(GlobalConst.CURRENT_USER);
        if(user == null){
            throw new ReturnViewException(ResponseEnum.NOT_LOGIN);
        }
        QuestionDTO question = questionService.getById(id);
        if(!question.getCreator().equals(user.getId())){
            throw new ReturnViewException(ResponseEnum.NO_EDIT_ARTICLE);
        }
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());

        model.addAttribute("tags", TagCache.get());
        model.addAttribute("articleType", ArticleTypeEnum.PUBLIC_ARTICLE.getCode());
        return "publish";
    }


    @GetMapping("/publish")
    public String publish(Model model) {

        model.addAttribute("tags", TagCache.get());

        return "publish";
    }

    //点击发布
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "articleType", required = false) Integer articleType,
            HttpServletRequest request,
            Model model) {

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        model.addAttribute("tags", TagCache.get());

        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "文章内容不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute(GlobalConst.CURRENT_USER);
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());

        //通过Id 判断是否是新增  还是 修改  若Id 为空 则为新增
        question.setId(id);  //可以为空

        if(ArticleTypeEnum.PRIVATE_ARTICLE.getCode().equals(articleType)){
            //表示是将私有文章公开
            questionService.insert(question);
        }else{
            questionService.createOrUpdate(question);
        }
        return "redirect:/";
    }
}
