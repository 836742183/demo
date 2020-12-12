package com.example.common.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: 王梓吉
 * @Date: 2020/12/12 16:44
 * @Description: 登录的用户名和密码验证数据传递
 **/
@Data
public class LoginDto implements Serializable {
    @NotBlank(message ="昵称不能为空")
    private String username;

    @NotBlank(message ="密码不能为空")
    private String password;
}
