package com.lxs.community.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

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




}
