package com.lxs.community.controller;

import com.lxs.community.dto.AccessTokenDTO;
import com.lxs.community.dto.GithubUser;
import com.lxs.community.mapper.UserMapper;
import com.lxs.community.model.User;
import com.lxs.community.provider.GithubProvider;
import com.lxs.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Mr.Li
 * @date 2019/8/6 - 19:36
 */
@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    //从配置文件中注入
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.client.uri}")
    private String clientUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){

        log.info("code = {}, state = {}", code, state);

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(clientUri);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);

        //通过accessToken获取 user
        GithubUser githubUser = githubProvider.getUser(accessToken);
        log.info("登录用户：{} ", githubUser.getName());

        //如果user不为null 说明登录成功。
        if(githubUser != null && githubUser.getId() != null){
            //向数据库中插入用户
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatar_url());

            //如果数据库有该用户则更新， 无创建
            userService.createOrUpdate(user);

            //登录成功， 写cookie和session
            //通过response写入cookie
            response.addCookie(new Cookie("token", token));
           // request.getSession().setAttribute("user", githubUser);
            //重定向到index页面去, 使uri不是redirect_uri=http://localhost:8887/callback&scope=user&state=1
            return "redirect:/";

        }else{
            log.error("callback get github error, {}", githubUser);
            //登录失败， 重新登陆
            return "redirect:/";

        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        //清除session
        request.getSession().removeAttribute("user");
        //清除cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0); //马上删除
        response.addCookie(cookie);


        return "redirect:/";
    }

    @GetMapping("/about")
    public String about(){

        return "about";
    }

}
