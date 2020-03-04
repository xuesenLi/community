package com.lxs.community.dto;

import lombok.Data;

/**
 * @author Mr.Li
 * @date 2019/8/13 - 17:46
 * <p>
 * 图片上传 DTO
 */

@Data
public class FileDTO {
    private int success;
    private String message;
    private String url;

}
