package com.lxs.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Mr.Li
 * @date 2019/8/9 - 17:12
 */
@Configuration
//@EnableWebMvc  //springmvc相关注解 写上会拦截静态资源
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //需要排出的拦截请求
        registry.addInterceptor(new SessionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/", "/error", "/user/**");
    }

/*    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }*/
}
