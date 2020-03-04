package com.lxs.community.mapper;

import com.lxs.community.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

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
    UserInfo selectByEmail(String email);


    UserInfo selectByToken(@Param("token") String token);

    /**
     * 通过accountId 查找
     *
     * @param accountId
     * @return
     */
    UserInfo selectByAccountId(@Param("accountId") String accountId);

}
