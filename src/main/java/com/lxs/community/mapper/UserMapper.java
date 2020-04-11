package com.lxs.community.mapper;

import com.lxs.community.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Set;

/**
 * @author Mr.Li
 * @date 2019/8/8 - 14:11
 */
@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 验证email是否存在
     *
     * @param email
     * @return
     */
    int countByEmail(String email);

    /**
     * 通过Email获取 userinfo
     *
     * @param email
     * @return
     */
    User selectByEmail(String email);


    User selectByToken(@Param("token") String token);

    /**
     * 通过accountId 查找
     *
     * @param accountId
     * @return
     */
    User selectByAccountId(@Param("accountId") String accountId);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name=#{name}, gmt_modified=#{gmtModified}, avatar_url=#{avatarUrl}, token=#{token} where id = #{id} ")
    void update(User user);

    List<User> selectByIdSet(@Param("fIdSet")Set<Integer> fIdSet);
}
