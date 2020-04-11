package com.lxs.community.enums;

import lombok.Getter;

/**
 * @author Mr.Li
 * @date 2020/4/1 - 13:30
 */
@Getter
public enum ArticleTypeEnum {

    PRIVATE_ARTICLE(0, "私有"),

    PUBLIC_ARTICLE(1, "公开"),

    DEFAULT_ARTICLE(2, "默认"),


    ;

    private Integer code;

    private String msg;

    ArticleTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
