package com.lxs.community.exception;

import com.lxs.community.enums.ResponseEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Mr.Li
 * @date 2020/3/30 - 14:35
 * 自定义返回视图的异常
 */
@Getter
@Setter
public class ReturnViewException extends RuntimeException{

    private Integer code;

    private String message;

    public ReturnViewException(ResponseEnum responseEnum){
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

}
