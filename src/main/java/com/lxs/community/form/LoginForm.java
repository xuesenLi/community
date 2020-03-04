package com.lxs.community.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Mr.Li
 * @date 2020/2/27 - 16:29
 */
@Data
public class LoginForm {

    @NotBlank(message = "您的邮箱不能为空")
    private String email;

    @NotBlank(message = "您的密码不能为空")
    private String password;
}
