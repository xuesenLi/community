package com.lxs.community.enums;

/**
 * @author Mr.Li
 * @date 2019/8/13 - 10:21
 *
 * 该通知的状态， 是否已被查看
 */

public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1);

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
