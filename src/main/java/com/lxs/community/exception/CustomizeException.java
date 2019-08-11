package com.lxs.community.exception;

/**
 * @author Mr.Li
 * @date 2019/8/10 - 15:02
 *
 * extends RuntimeException 继承这个异常 在调用时就不需要在try catch 了
 *
 * 通过这个异常类，
 */
public class CustomizeException extends RuntimeException{

    private String message;
    private Integer code;


    //
    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }


    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
