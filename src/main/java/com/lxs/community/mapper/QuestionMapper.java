package com.lxs.community.mapper;

import com.lxs.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Mr.Li
 * @date 2019/8/8 - 16:23
 */
@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title, description, gmt_create, gmt_modified, creator, tag) values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);

    @Select("select * from question order by gmt_create desc limit #{offset}, #{size}")
    List<Question> findByQuestionAll(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} order by gmt_create desc limit #{offset}, #{size}")
    List<Question> list(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);

    @Update("update question set title=#{title}, gmt_modified=#{gmtModified}, description=#{description}, tag=#{tag} where id = #{id}")
    int update(Question question);

    @Update("update question set view_count = view_count + 1 where id=#{id}")
    void updateViewCount(Integer id);

    @Update("update question set comment_count = comment_count + 1 where id=#{id}")
    void updateCommentCount(Integer id);

    List<Question> selectQuestionByTags(Question question);

}
