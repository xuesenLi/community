package com.lxs.community.controller;

import com.lxs.community.dto.MessageVO;
import com.lxs.community.dto.ResponseVO;
import com.lxs.community.dto.UserVO;
import com.lxs.community.enums.ResponseEnum;
import com.lxs.community.model.User;
import com.lxs.community.service.ChatService;
import com.lxs.community.utils.GlobalConst;
import com.lxs.community.webSocket.WebsocketServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Mr.Li
 * @date 2020/3/8 - 16:46
 */
@RestController
@Slf4j
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    /**
     * 获取指定用户的聊天消息内容
     *
     * @param toId   哪个窗口
     * @return
     */
    @GetMapping("/self/{toId}")
    public ResponseVO<List<MessageVO>> selfList(@PathVariable("toId") Integer toId,
                                          HttpSession session) {
        User user = (User) session.getAttribute(GlobalConst.CURRENT_USER);
        if(user == null){
            return ResponseVO.errorOf(ResponseEnum.NOT_LOGIN);
        }
        List<MessageVO> messageVOList = chatService.selfList(user.getId(), toId);
        log.info("messageVOList = {}", messageVOList);

        return ResponseVO.OkOf(messageVOList);
    }

    @PostMapping("/push")
    public ResponseVO push(@RequestBody MessageVO messageVO,
                           HttpSession session){
        User user = (User) session.getAttribute(GlobalConst.CURRENT_USER);

        UserVO userVO = this.convertFormUser(user);
        messageVO.setFrom(userVO);

        //TODO  通过websocket 通信去实现
        WebsocketServerEndpoint endpoint = new WebsocketServerEndpoint();

        return endpoint.sendTo(messageVO);

    }

    private UserVO convertFormUser(User user){
        if(user == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

}
