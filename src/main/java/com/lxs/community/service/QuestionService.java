package com.lxs.community.service;

import com.lxs.community.dto.PaginationDTO;
import com.lxs.community.dto.QuestionDTO;
import com.lxs.community.dto.QuestionQueryDTO;
import com.lxs.community.mapper.QuestionMapper;
import com.lxs.community.mapper.UserMapper;
import com.lxs.community.model.Question;
import com.lxs.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Li
 * @date 2019/8/9 - 11:10
 * <p>
 * 应用sevice目的：请求需要组装questionMapper与userMapper时 就使用这个中间层
 */
public interface QuestionService {

    //分页查询
    PaginationDTO list(QuestionQueryDTO questionQueryDTO, Integer page, Integer size);

    PaginationDTO list(Integer userId, Integer page, Integer size);

    QuestionDTO getById(Integer id);

    void createOrUpdate(Question question);

    void incView(Integer id);

    /**
     * 通过标签模糊查找
     *
     * @param questionDTO
     * @return
     */
    List<QuestionDTO> selectQuestionByTags(QuestionDTO questionDTO);
}
