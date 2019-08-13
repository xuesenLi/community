package com.lxs.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Mr.Li
 * @date 2019/8/12 - 23:37
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
