package com.lxs.community.mapper;

import com.lxs.community.model.User;
import org.apache.ibatis.annotations.*;

/**
 * @author Mr.Li
 * @date 2019/8/8 - 14:11
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user (name, account_id, token, gmt_create, gmt_modified, avatar_url) values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name=#{name}, gmt_modified=#{gmtModified}, avatar_url=#{avatarUrl}, token=#{token} where id = #{id} ")
    void update(User user);
}
