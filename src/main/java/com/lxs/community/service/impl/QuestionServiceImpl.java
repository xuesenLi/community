package com.lxs.community.service.impl;

import com.lxs.community.dto.PaginationDTO;
import com.lxs.community.dto.QuestionDTO;
import com.lxs.community.exception.CustomizeErrorCode;
import com.lxs.community.exception.CustomizeException;
import com.lxs.community.mapper.QuestionMapper;
import com.lxs.community.mapper.UserMapper;
import com.lxs.community.model.Question;
import com.lxs.community.model.User;
import com.lxs.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Li
 * @date 2019/8/10 - 17:17
 */
@Service
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    //分页查询
    public PaginationDTO list(Integer page, Integer size){


        //查询总数
        Integer totalCount = questionMapper.count();

        PaginationDTO paginationDTO = new PaginationDTO();
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

        List<Question> questions = questionMapper.findByQuestionAll(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question里面的属性拷贝到quetionDTO里面
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);

            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        //查询总数
        Integer totalCount = questionMapper.countByUserId(userId);

        PaginationDTO paginationDTO = new PaginationDTO();
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

        List<Question> questions = questionMapper.list(userId, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question里面的属性拷贝到quetionDTO里面
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);

            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {

        Question question = questionMapper.getById(id);
        //错误异常处理, 通过枚举  自定义异常
        if(question == null ){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        //再拿user对象
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);

        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            //新增
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else{
            //更新  只需要设置更新时间。
            question.setGmtModified(System.currentTimeMillis());

            int updated = questionMapper.update(question);
            if(updated != 1){
                //通过枚举  自定义异常
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    @Transactional
    public void incView(Integer id) {
        questionMapper.updateViewCount(id);
    }
}
