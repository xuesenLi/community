package com.lxs.community.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lxs.community.dto.MessageVO;
import com.lxs.community.dto.ResponseVO;
import com.lxs.community.service.ChatService;
import com.lxs.community.utils.RedisConst;
import com.lxs.community.utils.TimeUtil;
import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.Li
 * @date 2020/3/9 - 13:42
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public List<MessageVO> selfList(Integer formId, Integer toId) {
        List<MessageVO> list = new ArrayList<>();
        //A --> B
        String formTo = redisTemplate.opsForValue().get(RedisConst.CHAT_FROM_PREFIX + formId + RedisConst.CHAT_TO_PREFIX + toId);
        //B --> A
        String toForm = redisTemplate.opsForValue().get(RedisConst.CHAT_FROM_PREFIX + toId + RedisConst.CHAT_TO_PREFIX + formId);

        JSONArray formToArray = JSONObject.parseArray(formTo);
        JSONArray toFormArray = JSONObject.parseArray(toForm);
        if(formToArray != null){
            list.addAll(formToArray.toJavaList(MessageVO.class));
        }
        if(toFormArray != null){
            list.addAll(toFormArray.toJavaList(MessageVO.class));
        }
        //根据时间排序
        if(list.size() > 0){
            TimeUtil.sort(list);
            return list;
        }
        return new ArrayList<>();
    }

    @Override
    public ResponseVO pushMessage(MessageVO messageVO) {
        messageVO.setTime(TimeUtil.format(new Date()));
        String key = RedisConst.CHAT_FROM_PREFIX + messageVO.getFrom().getId() + RedisConst.CHAT_TO_PREFIX + messageVO.getTo().getId();
        this.push(messageVO, key);
        return ResponseVO.OkOf();
    }

    private void push(MessageVO messageVO, String key){
        List<MessageVO> list = new ArrayList<>();

        //根据key 查找redis中是否存在聊天记录
        String value = redisTemplate.opsForValue().get(key);
        if(value == null){
            //第一次推送消息
            list.add(messageVO);
        }else{
            //第n次推送
            list = JSONObject.parseArray(value).toJavaList(MessageVO.class);
            list.add(messageVO);
        }
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(list));
    }
}
