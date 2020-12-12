package com.example.shiro;

//import cn.hutool.http.server.HttpServerRequest;
import com.example.shiro.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 王梓吉
 * @Date: 2020/12/12 14:18
 * @Description: jwt验证
 **/
@Component
public class JwtFilter extends AuthenticatingFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        String jwt =request.getHeader("Authorization") ;
        if (StringUtils.isEmpty(jwt)){
            return null;
        }

        return new JwtToken((jwt));//拿到 token交给shiro进行登录处理

    }

    @Override
    //进行拦截
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request=(HttpServletRequest)servletRequest;
        String jwt =request.getHeader("Authorization") ;
        if (StringUtils.isEmpty(jwt)){//当没有jwt时，不需要交给shiro进行处理

            return true;

        }else {//当有jwt时
            //校验
            Claims claim = jwtUtil.getClaimByToken(jwt);
            if (claim ==null||jwtUtil.isTokenExpired(claim.getExpiration())){//为空或者过期
                throw new ExpiredCredentialsException("token已失效，请重新登录");
            }
            //登录处理
            return executeLogin(servletRequest,servletResponse);
        }

    }
}
