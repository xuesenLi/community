package com.lxs.community.controller;

import com.lxs.community.cache.TagCache;
import com.lxs.community.enums.ArticleTypeEnum;
import com.lxs.community.enums.ResponseEnum;
import com.lxs.community.exception.ReturnViewException;
import com.lxs.community.model.PrivateArticle;
import com.lxs.community.model.User;
import com.lxs.community.service.PrivateArticleService;
import com.lxs.community.utils.GlobalConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Mr.Li
 * @date 2020/3/30 - 13:32
 */
@Controller
@RequestMapping("/private-article")
public class PrivateArticleController {


    @Autowired
    private PrivateArticleService privateArticleService;

    @GetMapping("/{id}")
    public String privateArticle(@PathVariable(name = "id") Integer id,
                                 Model model){
        PrivateArticle privateArticle = privateArticleService.getById(id);
        model.addAttribute("privateArticle", privateArticle);
        return "privateArticle";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       HttpSession session,
                       Model model) {
        User user = (User) session.getAttribute(GlobalConst.CURRENT_USER);
        if(user == null){
            throw new ReturnViewException(ResponseEnum.NOT_LOGIN);
        }
        PrivateArticle privateArticle = privateArticleService.getById(id);
        if(!privateArticle.getCreator().equals(user.getId())){
            throw new ReturnViewException(ResponseEnum.NO_EDIT_ARTICLE);
        }
        model.addAttribute("title", privateArticle.getTitle());
        model.addAttribute("description", privateArticle.getDescription());
        model.addAttribute("tag", privateArticle.getTag());
        model.addAttribute("id", privateArticle.getId());

        model.addAttribute("tags", TagCache.get());

        model.addAttribute("articleType", ArticleTypeEnum.PRIVATE_ARTICLE.getCode());


        return "publish";
    }


    //点击发布
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Integer id,
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
            model.addAttribute("error", "文章描述不能为空");
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

        PrivateArticle privateArticle = new PrivateArticle();
        privateArticle.setTitle(title);
        privateArticle.setDescription(description);
        privateArticle.setTag(tag);
        privateArticle.setCreator(user.getId());

        //通过Id 判断是否是新增  还是 修改  若Id 为空 则为新增
        privateArticle.setId(id);  //可以为空
        privateArticleService.createOrUpdate(privateArticle);

        return "redirect:/";
    }

}
