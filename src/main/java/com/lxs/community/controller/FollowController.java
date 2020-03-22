package com.lxs.community.controller;

import com.lxs.community.dto.ResponseVO;
import com.lxs.community.enums.ResponseEnum;
import com.lxs.community.model.User;
import com.lxs.community.service.FollowService;
import com.lxs.community.utils.GlobalConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author Mr.Li
 * @date 2020/3/12 - 16:42
 */
@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PutMapping("/{fId}")
    public ResponseVO follow(@PathVariable("fId") Integer fId,
                             HttpSession session){
        User user = (User) session.getAttribute(GlobalConst.CURRENT_USER);
        if(user == null){
            return ResponseVO.errorOf(ResponseEnum.NOT_LOGIN);
        }
        return followService.follow(user.getId(), fId);
    }

    @DeleteMapping("/{fId}")
    public ResponseVO unFollow(@PathVariable("fId") Integer fId,
                               HttpSession session){
        User user = (User) session.getAttribute(GlobalConst.CURRENT_USER);
        if(user == null){
            return ResponseVO.errorOf(ResponseEnum.NOT_LOGIN);
        }
        return followService.unFollow(user.getId(), fId);
    }

    @GetMapping("/{fId}")
    public ResponseVO getFollow(@PathVariable("fId") Integer fId,
                               HttpSession session){
        User user = (User) session.getAttribute(GlobalConst.CURRENT_USER);
        if(user == null){
            return ResponseVO.errorOf(ResponseEnum.NOT_LOGIN);
        }
        return followService.getFollow(user.getId(), fId);
    }


}
