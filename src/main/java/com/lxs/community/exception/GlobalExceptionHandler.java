package com.lxs.community.exception;

import com.lxs.community.dto.ResponseVO;
import com.lxs.community.enums.ResponseEnum;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author Mr.Li
 * @date 2020/2/28 - 17:22
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseVO GlobalException(GlobalException e) {
        return ResponseVO.errorOf(ResponseEnum.ERROR, e.getMsg());
    }


    /**
     * 表单验证
     * 异常处理 @valid
     * 这样就不用在controller层去获取  BindingResult 对象了
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseVO notValidExceptionHandle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return ResponseVO.errorOf(ResponseEnum.PARAM_ERROR, bindingResult);
    }


}
