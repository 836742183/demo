package com.example.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: 王梓吉
 * @Date: 2020/12/12 14:33
 * @Description: 1、shiro默认supports的是UsernamePasswordToken，
 *                  而我们现在采用了jwt的方式，所以这里我们自定义一个JwtToken，
 *                  来完成shiro的supports方法。
 **/
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String jwt){//构造方法
        this.token=jwt;

    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
