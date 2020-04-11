package com.lxs.community.service.impl;

import com.lxs.community.dto.PaginationDTO;
import com.lxs.community.dto.PrivateArticleDTO;
import com.lxs.community.enums.ResponseEnum;
import com.lxs.community.exception.ReturnViewException;
import com.lxs.community.mapper.PrivateArticleMapper;
import com.lxs.community.mapper.UserMapper;
import com.lxs.community.model.PrivateArticle;
import com.lxs.community.model.User;
import com.lxs.community.service.PrivateArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mr.Li
 * @date 2020/3/30 - 15:33
 */
@Service
public class PrivateArticleServiceImpl implements PrivateArticleService {

    @Autowired
    private PrivateArticleMapper privateArticleMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public PrivateArticle getById(Integer id) {
        PrivateArticle privateArticle = privateArticleMapper.selectByPrimaryKey(id);
        if(privateArticle == null){
            throw new ReturnViewException(ResponseEnum.PRIVATE_ARTICLE_NOT_FOUND);
        }
        return privateArticle;
    }

    @Override
    public void createOrUpdate(PrivateArticle privateArticle) {
        if(privateArticle.getId() == null){
            //新增
            int count = privateArticleMapper.insertSelective(privateArticle);
            if(count != 1){
                throw new ReturnViewException(ResponseEnum.PRIVATE_ARTICLE_INSERT_FAIL);
            }
        }else{
            //修改
            int count = privateArticleMapper.updateByPrimaryKeySelective(privateArticle);
            if(count != 1){
                throw new ReturnViewException(ResponseEnum.PRIVATE_ARTICLE_INSERT_FAIL);
            }
        }
    }

    @Override
    public PaginationDTO list(Integer id, Integer page, Integer size) {
        List<PrivateArticle> privateArticles = privateArticleMapper.selectByCreator(id);
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setData(privateArticles);
        return paginationDTO;
    }
}
