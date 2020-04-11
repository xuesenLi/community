package com.lxs.community.mapper;

import com.lxs.community.model.PrivateArticle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PrivateArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PrivateArticle record);

    int insertSelective(PrivateArticle record);

    PrivateArticle selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PrivateArticle record);

    int updateByPrimaryKey(PrivateArticle record);

    List<PrivateArticle> selectByCreator(Integer creator);

}
