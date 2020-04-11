package com.lxs.community.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * @author Mr.Li
 * @date 2019/8/13 - 17:46
 * 文件上传，
 */
@Controller
@Slf4j
@RequestMapping("/md")
public class FileController {

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                Model model) {
        if (file.isEmpty()) {
            model.addAttribute("error", "文件不能为空");
            return "publish";
        }

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        log.info("suffix = {}", suffix);
        if(!"md".equals(suffix)){
            model.addAttribute("error", "文件的拓展名必须为 .md");
            return "publish";
        }

        try {

            byte[] bytes = file.getBytes();
            String content=new String(bytes,"UTF-8");

           model.addAttribute("description", content);
           model.addAttribute("title", fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "publish";
    }

}
