package com.lxs.community.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Mr.Li
 * @date 2020/3/30 - 14:39
 */
@ControllerAdvice
public class ReturnViewExceptionHandler {


    @ExceptionHandler(ReturnViewException.class)
    ModelAndView ReturnViewException(ReturnViewException e, Model model){
        model.addAttribute("message", e.getMessage());
        return new ModelAndView("error");
    }

}
