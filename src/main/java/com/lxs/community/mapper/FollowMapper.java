package com.lxs.community.mapper;

import com.lxs.community.model.Follow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FollowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Follow record);

    int insertSelective(Follow record);

    Follow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Follow record);

    int updateByPrimaryKey(Follow record);

    /**
     * 取消关注用户
     * @param uId
     * @param fId
     * @return
     */
    int deleteByUidAndFid(@Param("uId") Integer uId, @Param("fId") Integer fId);


    Follow selectByUidAndFid(@Param("uId") Integer uId, @Param("fId") Integer fId);

    List<Follow> selectByUid(Integer uId);

}
