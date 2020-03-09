package com.lxs.community.utils;

/**
 * @author Mr.Li
 * @date 2020/3/9 - 13:47
 */
public class RedisConst {

    /**
     * 用户数据key 前缀标识
     */
    public static final String USER_PREFIX = "USER_";

    /**
     *推送至指定用户消息
     *  推送方 Key前缀标识
     */
    public static final String CHAT_FROM_PREFIX = "CHAT_FROM_";


    /**
     * 推送至指定用户消息
     *      接收方 Key前缀标识
     */
    public static final String CHAT_TO_PREFIX = "_TO_";

    /**
     * RedisTemplate 根据Key模糊匹配查询前缀
     */
    public static final String REDIS_MATCH_PREFIX = "*";

}
