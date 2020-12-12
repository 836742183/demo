package com.example.common.exception;

import com.example.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * @Author: 王梓吉
 * @Date: 2020/12/12 16:10
 * @Description: 全局异常处理
 *
 * 有时候不可避免服务器报错的情况，如果不配置异常处理机制，
 * 就会默认返回tomcat或者nginx的5XX页面，对普通用户来说，
 * 不太友好，用户也不懂什么情况。这时候需要我们程序员设计返
 * 回一个友好简单的格式给前端。
 *
 * 处理办法如下：通过使用@ControllerAdvice来进行统一异常处理，
 * @ExceptionHandler(value = RuntimeException.class)来指定捕获的Exception各个类型异常 ，
 * 这个异常的处理，是全局的，所有类似的异常，都会跑到这个地方处理。
 *
 **/
@Slf4j//日志输出
@RestControllerAdvice
public class GlobalExceptionHandler {
/*
* @ControllerAdvice表示定义全局控制器异常处理，
* @ExceptionHandler表示针对性异常处理，可对每种异常针对性处理。
* */

    /**
     * @Author: 王梓吉
     * @Date: 2020/12/12 16:21
     * @Description: 捕获shiro异常
     **/
    @ResponseStatus(HttpStatus.UNAUTHORIZED)//返回状态码给前端
    @ExceptionHandler(value = ShiroException.class)
    public Result handler(ShiroException e){
        log.error("运行时异常：---------",e);
        return Result.fail(401,e.getMessage(),null);
    }

    /**
     * @Author: 王梓吉
     * @Date: 2020/12/12 16:23
     * @Description: 处理Assert的异常
     **/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) throws IOException {
        log.error("断言异常:-------------->{}",e.getMessage());
        return Result.fail(e.getMessage());
    }
    
    /**
     * @Author: 王梓吉
     * @Date: 2020/12/12 16:22
     * @Description:  @Validated 校验错误异常处理
     **/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) throws IOException {
        log.error("实体校验异常:-------------->",e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.fail(objectError.getDefaultMessage());
    }

    /**
     * @Author: 王梓吉
     * @Date: 2020/12/12 16:22
     * @Description: 捕获其他异常
     **/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e){
        log.error("其他异常：---------",e);
        return Result.fail(e.getMessage());
    }
}
