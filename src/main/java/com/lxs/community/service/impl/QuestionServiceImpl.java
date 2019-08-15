package com.lxs.community.service.impl;

import com.lxs.community.dto.PaginationDTO;
import com.lxs.community.dto.QuestionDTO;
import com.lxs.community.dto.QuestionQueryDTO;
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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
    public PaginationDTO list(String search, String tag, Integer page, Integer size){

        //需要分割search 但是search不能为空
        if(search != null && search != ""){
            String[] split = search.split(" ");
            search = Arrays.stream(split).collect(Collectors.joining("|"));
        }

        //查询总数
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        questionQueryDTO.setTag(tag);

        Integer totalCount = questionMapper.countBySearch(questionQueryDTO);
        //如果totalCount == 0， 就没必要在做后面分页查找的 操作了
        if(totalCount == 0){
            return new PaginationDTO();
        }
        
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
        questionQueryDTO.setPage(offset);
        questionQueryDTO.setSize(size);
        List<Question> questions = questionMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question里面的属性拷贝到quetionDTO里面
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);

            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);


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
        paginationDTO.setData(questionDTOList);

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

    @Override
    public List<QuestionDTO> selectQuestionByTags(QuestionDTO questionDTO) {
        if(questionDTO.getTag() == null || questionDTO.getTag() == ""){
            return new ArrayList<>();
        }

       // String[] tags = StringUtils.split(questionDTO.getTag(), ",");
        String[] tags = questionDTO.getTag().split(",");
        //在通过 ‘|’ 符号拼接 ，  sql语句才能识别  正则匹配
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        System.out.println(regexpTag);

        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionMapper.selectQuestionByTags(question);

        List<QuestionDTO> questionDTOList = questions.stream().map(q -> {
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO1);
            return questionDTO1;
        }).collect(Collectors.toList());


        return questionDTOList;
    }

}
