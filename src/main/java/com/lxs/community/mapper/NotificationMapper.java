package com.lxs.community.mapper;

import com.lxs.community.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Mr.Li
 * @date 2019/8/13 - 10:01
 */
@Mapper
public interface NotificationMapper {

    @Insert("insert into notification (notifier, receiver, outerid, gmt_create, type, status, notifier_name, outer_title) values (#{notifier}, #{receiver}, #{outerid}, #{gmtCreate}, #{type}, #{status}, #{notifierName}, #{outerTitle})")
    void insert(Notification notification);

/*    @Select("select count(1) from notification")
    Integer count();*/

    @Select("select count(1) from notification where receiver = #{receiver}")
    Integer countByReceiver(@Param("receiver") Integer receiver);

    @Select("select * from notification where receiver=#{receiver} order by gmt_create desc limit #{offset}, #{size}")
    List<Notification> list(@Param("receiver") Integer receiver, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from notification where receiver=#{receiver} and status=#{status}")
    Integer selectCountByUnRead(@Param("receiver") Integer id, @Param("status") int status);

    @Select("select * from notification where id = #{id}")
    Notification selectById(Integer id);

    @Update("update notification set status = #{status} where id = #{id}")
    void updateByPrimaryKey(Notification notification);
}
