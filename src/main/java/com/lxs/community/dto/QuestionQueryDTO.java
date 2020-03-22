package com.lxs.community.dto;

import lombok.Data;

import java.util.Set;

/**
 * @author Mr.Li
 * @date 2019/8/13 - 23:28
 * 封装的 搜索功能 DTO
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
    private String tag;

    //sort = follow, hot, my
    private String sort;

    //当前登录用户
    private Integer creator;

    private Set<Integer> creatorSet;



}
