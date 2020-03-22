package com.lxs.community.service.impl;

import com.lxs.community.dto.NotificationDTO;
import com.lxs.community.dto.PaginationDTO;
import com.lxs.community.enums.NotificationStatusEnum;
import com.lxs.community.enums.NotificationTypeEnum;
import com.lxs.community.exception.CustomizeErrorCode;
import com.lxs.community.exception.CustomizeException;
import com.lxs.community.mapper.NotificationMapper;
import com.lxs.community.model.Notification;
import com.lxs.community.model.User;
import com.lxs.community.service.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Mr.Li
 * @date 2019/8/13 - 10:03
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public PaginationDTO list(Integer receiver, Integer page, Integer size) {

        //查询当前用户接受通知的总数,
        Integer totalCount = notificationMapper.countByReceiver(receiver);
        if(totalCount == 0)
            return new PaginationDTO();

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        //计算分页显示的图标
        paginationDTO.setPagination(totalCount, page, size);
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }


        //当前页的第一条数据
        Integer offset = size * (page - 1);

        //分页查找并且更具当前登录的用户Id = receiver（接受者的Id）
        List<Notification> notifications = notificationMapper.list(receiver, offset, size);

        if (notifications.size() == 0) {
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));

            notificationDTOS.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOS);


        return paginationDTO;
    }

    @Override
    public Integer unreadCount(Integer id) {
        return notificationMapper.selectCountByUnRead(id, NotificationStatusEnum.UNREAD.getStatus());
    }

    @Override
    public NotificationDTO read(Integer id, User user) {
        Notification notification = notificationMapper.selectById(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        //将status的值改为已读类型
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));

        return notificationDTO;
    }
}
