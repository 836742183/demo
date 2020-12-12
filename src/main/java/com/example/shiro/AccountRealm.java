package com.example.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

/**
 * @Author: 王梓吉
 * @Date: 2020/12/12 14:05
 * @Description: AccountRealm是shiro进行登录或者权限校验的逻辑所在，算是核心了，我们需要重写3个方法，分别是
 *                  1. supports：为了让realm支持jwt的凭证校验
 *                  2. doGetAuthorizationInfo：权限校验
 *                  3. doGetAuthenticationInfo：登录认证校验
 *
 **/
@Component
public class AccountRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        //判断token是否是jwt中的token
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        /**
         * @Author: 王梓吉
         * @param: principalCollection
         * @Return: AuthorizationInfo
         * @Date: 2020/12/12 14:09
         * @Description: 拿到用户之后获取权限，封装成AuthorizationInfo类返回给Shiro
         **/
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        /**
         * @Author: 王梓吉
         * @param: authenticationToken
         * @Return: AuthenticationInfo
         * @Date: 2020/12/12 14:08
         * @Description: 通过Token返回用户基本信息
         **/
        JwtToken jwtToken=(JwtToken) token;

        System.out.println("---------");

        return null;
    }
}
