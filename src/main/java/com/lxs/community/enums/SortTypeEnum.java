package com.lxs.community.enums;

import lombok.Getter;

/**
 * @author Mr.Li
 * @date 2020/3/17 - 16:11
 */
@Getter
public enum SortTypeEnum {

    FOLLOW(1, "follow"),

    HOT(2, "hot"),

    MY(3, "my"),

    ;

    private Integer code;

    private String type;

    SortTypeEnum(Integer code, String type){
        this.code = code;
        this.type = type;
    }



}
