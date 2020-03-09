package com.lxs.community.dto;

import com.lxs.community.model.User;
import lombok.Data;


/**
 * @author Mr.Li
 * @date 2020/3/8 - 16:49
 */
@Data
public class MessageVO {

    /**
     * 消息推送者
     */
    private UserVO from;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 消息接收者：
     * 如果是私有（向指定窗口推送），to即为接受者User对象
     * 如果是公共消息（群组聊天），to设为null
     */
    private UserVO to;

    /**
     * 创建时间
     */
    private String time;
}
