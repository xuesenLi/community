package com.lxs.community.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mr.Li
 * @date 2020/2/28 - 17:22
 * <p>
 * 自定义全局捕获异常
 */
public class GlobalException extends RuntimeException {

    @Getter
    @Setter
    private String msg;

    public GlobalException(String msg) {
        this.msg = msg;
    }

}
