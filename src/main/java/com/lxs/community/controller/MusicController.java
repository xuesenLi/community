package com.lxs.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Mr.Li
 * @date 2019/12/24 - 15:11
 *
 */
@Controller
public class MusicController {

    @GetMapping("/music")
    public String music(){
        System.out.println("进入Music");
        return "music";
    }

}
