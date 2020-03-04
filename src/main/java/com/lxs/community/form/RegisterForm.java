package com.lxs.community.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Mr.Li
 * @date 2020/2/28 - 17:17
 */
@Data
public class RegisterForm {

    @NotBlank(message = "您的邮箱不能为空")
    private String email;

    @NotBlank(message = "您的密码不能为空")
    private String password;

    @NotBlank(message = "您的昵称不能为空")
    private String name;

    private String bio;

    @NotBlank(message = "您的头像不能为空")
    private String avatarUrl;
}
