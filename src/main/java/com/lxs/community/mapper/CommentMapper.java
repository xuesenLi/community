package com.lxs.community.mapper;

import com.lxs.community.enums.CommentTypeEnum;
import com.lxs.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Mr.Li
 * @date 2019/8/10 - 16:26
 */
@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id, type, commentator, gmt_create, gmt_modified, like_count, content) values (#{parentId}, #{type}, #{commentator}, #{gmtCreate}, #{gmtModified}, #{likeCount}, #{content})")
    void insert(Comment comment);

    @Select("select * from comment where id=#{id}")
    Comment selectById(Comment comment);

    @Select("select * from comment where parent_id=#{parentId} and type=#{type}")
    List<Comment> selectByParentId(@Param("parentId") Integer id, @Param("type") Integer type);
}
