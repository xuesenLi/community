package com.lxs.community.service;

import com.lxs.community.dto.MessageVO;
import com.lxs.community.dto.ResponseVO;
import com.lxs.community.dto.UserVO;

import java.util.List;

/**
 * @author Mr.Li
 * @date 2020/3/9 - 13:41
 */
public interface ChatService {


    /**
     *获取该用户与指定用户的推送消息
     * @param formId
     * @param toId
     * @return
     */
    List<MessageVO> selfList(Integer formId, Integer toId);


    ResponseVO pushMessage(MessageVO messageVO);
}
