package com.lxs.community.interceptor;

import com.lxs.community.enums.ResponseEnum;
import com.lxs.community.exception.GlobalException;
import com.lxs.community.mapper.UserMapper;
import com.lxs.community.model.User;
import com.lxs.community.service.NotificationService;
import com.lxs.community.utils.GlobalConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mr.Li
 * @date 2019/8/9 - 17:15
 */
@Slf4j
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       /* Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);

                        //返回当前用户未查看的通知
                        Integer unreadCount = notificationService.unreadCount(user.getId());
                        request.getSession().setAttribute("unreadCount", unreadCount);

                    }
                    break;
                }
            }
        }*/
        User user = (User) request.getSession().getAttribute(GlobalConst.CURRENT_USER);
        if(user == null){
            //需要返回我们的ResponseVo json数据
            //1. response.getWriter.print("json串");
            //2. 抛出自定义异常。。
            throw new GlobalException("您还未登录");
            // return ResponseVo.error(ResponseEnum.NEED_LOGIN);
//            response.sendRedirect("/error");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
