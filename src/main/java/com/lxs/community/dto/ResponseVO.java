package com.lxs.community.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lxs.community.enums.ResponseEnum;
import com.lxs.community.exception.CustomizeErrorCode;
import com.lxs.community.exception.CustomizeException;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * @author Mr.Li
 * @date 2019/8/10 - 16:58
 * <p>
 * 当有属性为null时，就不把它返回回去了。
 * @JsonInclude(value = JsonInclude.Include.NON_NULL)
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseVO<T> {

    private Integer code;

    private String message;

    private T data;

    private ResponseVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private ResponseVO(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> ResponseVO<T> errorOf(Integer code, String message) {
        return new ResponseVO<T>(code, message);
    }

    public static <T> ResponseVO<T> errorOf(ResponseEnum responseEnum, String message) {
        return new ResponseVO<T>(responseEnum.getCode(), message);
    }

    public static <T> ResponseVO<T> errorOf(ResponseEnum responseEnum, BindingResult bindingResult) {
        return new ResponseVO<T>(responseEnum.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getField() + " " +
                bindingResult.getFieldError().getDefaultMessage());
    }


    public static <T> ResponseVO<T> errorOf(CustomizeErrorCode errorCode) {
        return new ResponseVO<T>(errorCode.getCode(), errorCode.getMessage());
    }

    //错误处理。。
    public static <T> ResponseVO<T> errorOf(CustomizeException e) {
        return new ResponseVO<T>(e.getCode(), e.getMessage());
    }

    public static <T> ResponseVO<T> errorOf(ResponseEnum responseEnum) {
        return new ResponseVO<T>(responseEnum.getCode(), responseEnum.getMessage());
    }

    public static <T> ResponseVO<T> OkOf() {
        return new ResponseVO<T>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage());
    }

    public static <T> ResponseVO<T> OkOf(T data) {
        return new ResponseVO<T>(ResponseEnum.SUCCESS.getCode(), data);
    }

    public static <T> ResponseVO<T> OkOf(ResponseEnum responseEnum) {
        return new ResponseVO<T>(responseEnum.getCode(), responseEnum.getMessage());
    }


}
