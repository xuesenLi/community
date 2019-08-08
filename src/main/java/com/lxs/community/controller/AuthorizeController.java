package com.lxs.community.controller;

import com.lxs.community.dto.AccessTokenDTO;
import com.lxs.community.dto.GithubUser;
import com.lxs.community.mapper.UserMapper;
import com.lxs.community.model.User;
import com.lxs.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Mr.Li
 * @date 2019/8/6 - 19:36
 */
@Controller
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
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(clientUri);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);

        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser.getName());
        //如果user不为null 说明登录成功。
        if(githubUser != null){
            //向数据库中插入用户
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());

            userMapper.insert(user);

            //登录成功， 写cookie和session
            //通过response写入cookie
            response.addCookie(new Cookie("token", token));
           // request.getSession().setAttribute("user", githubUser);
            //重定向到index页面去, 使uri不是redirect_uri=http://localhost:8887/callback&scope=user&state=1
            return "redirect:/";

        }else{
            //登录失败， 重新登陆
            return "redirect:/";

        }
    }
}
