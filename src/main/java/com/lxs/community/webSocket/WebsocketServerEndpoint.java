package com.lxs.community.webSocket;

import com.alibaba.fastjson.JSON;
import com.lxs.community.dto.MessageVO;
import com.lxs.community.dto.ResponseVO;
import com.lxs.community.enums.ResponseEnum;
import com.lxs.community.service.ChatService;
import com.lxs.community.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Mr.Li
 * @date 2020/3/9 - 17:15
 *
 *  WebSocket
 */
@Slf4j
@Component
@ServerEndpoint(value = "/chat/{id}")
public class WebsocketServerEndpoint {

    private static ChatService chatService;

    @Autowired
    public void setChatSessionService(ChatService chatSessionService) {
        WebsocketServerEndpoint.chatService = chatSessionService;
    }

    //在线连接数
    private static long online = 0;

    //用于存放当前Websocket对象的Set集合
    private static CopyOnWriteArraySet<WebsocketServerEndpoint> websocketServerEndpoints = new CopyOnWriteArraySet<>();

    //与客户端的会话Session
    private Session session;

    //当前会话窗口ID
    private Integer fromId;


    /*链接成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("id") Integer id){
        log.info("WebSocket.onOpen >> 链接成功");
        this.session = session;

        //将当前websocket对象存入到Set集合中
        websocketServerEndpoints.add(this);

        //在线人数+1
        addOnlineCount();

        this.fromId = id;
        log.info("有新窗口开始监听：" + this.fromId + ", 当前在线人数为：" + getOnlineCount());

    }

    @OnClose
    public void onClose(){
        log.info("onClose >> 链接关闭");
        //移除当前Websocket对象
        websocketServerEndpoints.remove(this);

        //在内线人数-1
        subOnLineCount();
        log.info("链接关闭，当前在线人数：" + getOnlineCount());
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    /*收到客服端消息后调用的方法*/
    @OnMessage
    public void onMessage(String message) throws IOException{
        log.info("接收到账号: " + this.fromId + "的消息" + message);

    }

    /*推送消息*/
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /*封装返回消息*/
    private String getData(MessageVO messageVO){
        return JSON.toJSONString(ResponseVO.OkOf(messageVO));
    }





    public ResponseVO sendTo(MessageVO messageVO){
        messageVO.setTime(TimeUtil.format(new Date()));

        //判断用户是否在线
        boolean flag = false;
        //查看该用户是否上线  上线: 推送消息，   不在线:不推送  但都要保存在redis中
        for (WebsocketServerEndpoint endpoint : websocketServerEndpoints) {
            try {
                if (endpoint.fromId.equals(messageVO.getTo().getId())) {
                    flag = true;
                    //当前用户在线
                    log.info("用户: "+ messageVO.getFrom().getId() + " 推送消息到在线用户：" + messageVO.getTo().getId() + " ，推送内容：" + messageVO.getMessage());
                    endpoint.sendMessage(this.getData(messageVO)); //给接收方推送消息
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        log.info("将数据保存在redis中");
        //将推送消息保存在redis中
        ResponseVO responseVO = chatService.pushMessage(messageVO);

        if(!flag){
            log.info("用户 : " + messageVO.getTo().getName() + "不在线");
            return ResponseVO.OkOf(ResponseEnum.USER_NOT_ONLINE);
        }

       return responseVO;
    }









    private void subOnLineCount() {
        WebsocketServerEndpoint.online--;
    }

    private synchronized long getOnlineCount() {
        return online;
    }

    private void addOnlineCount() {
        WebsocketServerEndpoint.online++;
    }

}
