package com.lxs.community.dto;

import lombok.Data;

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

}
