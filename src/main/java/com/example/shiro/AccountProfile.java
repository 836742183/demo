package com.example.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 王梓吉
 * @Date: 2020/12/12 15:47
 * @Description: 返回信息封装，登录成功之后返回的一个用户信息的载体
 **/
@Data
public class AccountProfile implements Serializable {
    private Long id;

    private String username;

    private String avatar;

    private String email;
}
