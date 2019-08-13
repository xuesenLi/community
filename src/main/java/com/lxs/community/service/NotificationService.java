package com.lxs.community.service;

import com.lxs.community.dto.NotificationDTO;
import com.lxs.community.dto.PaginationDTO;
import com.lxs.community.model.User;

/**
 * @author Mr.Li
 * @date 2019/8/13 - 10:02
 */
public interface NotificationService {
    PaginationDTO list(Integer id, Integer page, Integer size);

    Integer unreadCount(Integer id);

    NotificationDTO read(Integer id, User user);
}
