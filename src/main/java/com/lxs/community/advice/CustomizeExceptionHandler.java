package com.lxs.community.advice;

import com.alibaba.fastjson.JSON;
import com.lxs.community.dto.ResponseVO;
import com.lxs.community.exception.CustomizeErrorCode;
import com.lxs.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Mr.Li
 * @date 2019/8/10 - 14:54
 * <p>
 * 页面异常处理
 */

/*@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {


        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            ResponseVO responseVO = null;
            //返回json
            if(e instanceof CustomizeException){
                responseVO =  ResponseVO.errorOf((CustomizeException) e);
            }else{
                responseVO =  ResponseVO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(responseVO));

                writer.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            return null;
        }else{
            //错误页面跳转
            //先看是否为我们自定义的异常
            if(e instanceof CustomizeException){
                model.addAttribute("message", e.getMessage());
            }else{
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }


}*/
