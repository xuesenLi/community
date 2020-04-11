package com.lxs.community.enums;

import lombok.Getter;

/**
 * @author Mr.Li
 * @date 2020/2/27 - 16:45
 * <p>
 * 返回的错误状态码
 */
@Getter
public enum ResponseEnum {

    SUCCESS(200, "请求成功"),

    ERROR(500, "服务器端错误"),

    PARAM_ERROR(1, "参数错误"),

    EMAIL_EXIST(2, "该邮箱已存在"),

    EMAIL_OR_PASSWORD_ERROR(3, "邮箱或者密码错误"),

    NOT_LOGIN(4, "您还未登录"),

    USER_EXIST(5, "当前用户已被注销或删除"),

    USER_NOT_ONLINE(6, "对方当前不在线"),

    USER_FOLLOW_FAIL(7, "用户关注失败"),

    USER_UN_FOLLOW_FAIL(8, "取消用户关注失败"),

    NO_EDIT_ARTICLE(9, "您还没有编辑该文章权限"),

    PRIVATE_ARTICLE_NOT_FOUND(10, "你找的私有文章不在了， 要不要换个试试？"),

    PRIVATE_ARTICLE_INSERT_FAIL(11, "你插入的私有文章失败，请重试"),

    PRIVATE_ARTICLE_UPDATE_FAIL(12, "你修改的私有文章失败，请重试"),

    PRIVATE_TRANSFER_PUBLIC(13, "将文章公开遇到一个错误"),


    ;

    private Integer code;

    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
