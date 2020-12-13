package com.example.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.shiro.utils.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserService userService;

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
         * @Description: 登录方法，通过登录校验获取Token信息返回用户基本信息和数据库的数据进行对比
         **/
        JwtToken jwtToken=(JwtToken) token;//已获取jwt
        String userId = jwtUtil.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();

        User user = userService.getById(Long.valueOf(userId));//与数据库数据进行对比
        if (user==null){
            throw new UnknownAccountException("账户不存在");
        }
        if (user.getStatus()==-1){//用户状态被锁定
            throw new LockedAccountException("账户已被锁定");
        }
        AccountProfile profile =new AccountProfile();
        BeanUtil.copyProperties(user,profile); //user→profile当中

        System.out.println("---------");

        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());//返回非私密信息
    }
}
