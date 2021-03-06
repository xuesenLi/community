package com.lxs.community.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Mr.Li
 * @date 2020/3/3 - 13:52
 *
 * 页面跳转
 */
@Controller
public class PageJumpController {

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    /**
     * 跳转到login.thml
     *
     * @return
     */
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    /**
     * 跳转到chat.html
     * @return
     */
    @GetMapping("/chat")
    public String chat(){
        return "chat/chat";
    }

    /**
     * 跳转到error.html
     * @return
     */
    @GetMapping("/error")
    public String error(){

        return "error";
    }

    /**
     * 跳转到people.html
     */
    @GetMapping("people/{id}")
    public String toPeople(@PathVariable("id") Integer id){
        return "chat/people";
    }


}
