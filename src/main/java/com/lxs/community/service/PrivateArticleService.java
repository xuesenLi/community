package com.lxs.community.service;

import com.lxs.community.dto.PaginationDTO;
import com.lxs.community.dto.PrivateArticleDTO;
import com.lxs.community.model.PrivateArticle;

/**
 * @author Mr.Li
 * @date 2020/3/30 - 15:33
 */
public interface PrivateArticleService {

    PrivateArticle getById(Integer id);

    void createOrUpdate(PrivateArticle privateArticle);

    PaginationDTO list(Integer id, Integer page, Integer size);
}
