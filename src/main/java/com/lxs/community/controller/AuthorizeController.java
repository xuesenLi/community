package com.lxs.community.controller;

import com.lxs.community.dto.AccessTokenDTO;
import com.lxs.community.dto.GithubUser;
import com.lxs.community.dto.ResponseVO;
import com.lxs.community.enums.ResponseEnum;
import com.lxs.community.form.LoginForm;
import com.lxs.community.form.RegisterForm;
import com.lxs.community.mapper.UserMapper;
import com.lxs.community.model.User;
import com.lxs.community.provider.GithubProvider;
import com.lxs.community.service.UserService;
import com.lxs.community.utils.GlobalConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

    @Autowired
    private UserService userService;

    //从配置文件中注入
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.client.uri}")
    private String clientUri;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response,
                           HttpSession session) {

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
        if (githubUser != null && githubUser.getId() != null) {
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

            //TODO 这里没有user.id
            //session.setAttribute(GlobalConst.CURRENT_USER, user);
            // request.getSession().setAttribute("user", githubUser);
            //重定向到index页面去, 使uri不是redirect_uri=http://localhost:8887/callback&scope=user&state=1
            return "redirect:/";

        } else {
            log.error("callback get github error, {}", githubUser);
            //登录失败， 重新登陆
            return "redirect:/";

        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        //清除cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0); //马上删除
        response.addCookie(cookie);

        //清除session
        request.getSession().removeAttribute("user");
        //request.getSession().invalidate();

        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/user/login")
    public ResponseVO login(@Valid @RequestBody LoginForm form,
                            HttpServletResponse response,
                            HttpSession session) {

        log.info("email: {},  password: {}", form.getEmail(), form.getPassword());
        //登录验证

        ResponseVO<User> login = userService.login(form.getEmail(), form.getPassword());


        //设置token
        Cookie cookie = new Cookie("token", login.getData().getToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        response.addCookie(cookie);

        //session.setAttribute(GlobalConst.CURRENT_USER, login.getData());
        return login;
    }


    /**
     * 用户注册
     *
     * @param form
     * @return
     */
    @ResponseBody
    @PostMapping("/user/register")
    public ResponseVO register(@Valid @RequestBody RegisterForm form,
                               HttpServletResponse response,
                               HttpSession session) {
        log.info("LoginForm : {} ", form.toString());

        User user = new User();

        //将 registerForm --> User
        BeanUtils.copyProperties(form, user);
        ResponseVO<User> register = userService.register(user);

        //设置token
        Cookie cookie = new Cookie("token", register.getData().getToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        response.addCookie(cookie);

        //session.setAttribute(GlobalConst.CURRENT_USER, register.getData());
        return register;
    }

    @GetMapping("/user/get")
    @ResponseBody
    public ResponseVO<User> getUser(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return ResponseVO.errorOf(ResponseEnum.NOT_LOGIN);
        }
        return ResponseVO.OkOf(user);
    }

    @GetMapping("user/{id}")
    @ResponseBody
    public ResponseVO<User> getUserById(@PathVariable("id") Integer id){
        return userService.selectById(id);
    }

}
