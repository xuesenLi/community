package com.lxs.community.controller;

import com.lxs.community.dto.ResponseVO;
import com.lxs.community.model.User;
import com.lxs.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mr.Li
 * @date 2020/3/6 - 16:23
 */
@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private UserService userService;




}
