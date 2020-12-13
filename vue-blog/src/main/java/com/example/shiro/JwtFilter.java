package com.example.shiro;

//import cn.hutool.http.server.HttpServerRequest;

import cn.hutool.json.JSONUtil;
import com.example.common.lang.Result;
import com.example.shiro.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 王梓吉
 * @Date: 2020/12/12 14:18
 * @Description: jwt验证，定义jwt的过滤器JwtFilter。
 *              这个过滤器是我们的重点，这里我们继承的是Shiro内置的AuthenticatingFilter，一个可以内置了可以自动登录方法的的过滤器，有些同学继承BasicHttpAuthenticationFilter也是可以的。
 * 我们需要重写几个方法：
 *
 * createToken：实现登录，我们需要生成我们自定义支持的JwtToken
 * onAccessDenied：拦截校验，当头部没有Authorization时候，我们直接通过，不需要自动登录；当带有的时候，首先我们校验jwt的有效性，没问题我们就直接执行executeLogin方法实现自动登录
 * onLoginFailure：登录异常时候进入的方法，我们直接把异常信息封装然后抛出
 * preHandle：拦截器的前置拦截，因为我们是前后端分析项目，项目中除了需要跨域全局配置之外，我们再拦截器中也需要提供跨域支持。这样，拦截器才不会在进入Controller之前就被限制了。
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

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        HttpServletResponse httpServletResponse =(HttpServletResponse) response;

        //获取错误原因
        Throwable throwable = e.getCause() == null ? e : e.getCause();

        Result result = Result.fail(throwable.getMessage());

        String json = JSONUtil.toJsonStr(result);

        try {
            httpServletResponse.getWriter().print(json);
        } catch (IOException ioException) {

        }
        return false;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        /**
         * @Author: 王梓吉
         * @Date: 2020/12/12 16:40
         * @Description: 对跨域问题提供支持
         **/
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
    }

