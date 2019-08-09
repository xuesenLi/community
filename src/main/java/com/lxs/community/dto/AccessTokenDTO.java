package com.lxs.community.dto;

import lombok.Data;

/**
 * @author Mr.Li
 * @date 2019/8/6 - 19:48
 */
@Data
public class AccessTokenDTO {
    private String client_id;

    private String client_secret;

    private String code;

    private String redirect_uri;

    private String state;

}
