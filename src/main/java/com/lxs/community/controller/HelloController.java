package com.lxs.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Mr.Li
 * @date 2019/8/6 - 16:35
 */
@Controller
public class HelloController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

}
