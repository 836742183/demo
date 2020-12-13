package com.example.controller;


import com.example.common.lang.Result;
import com.example.entity.User;
import com.example.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wizze.J
 * @since 2020-12-12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequiresAuthentication//需要认证进行访问
    @GetMapping("/index")
    public Object index(){
        User user =userService.getById(1L);
        return Result.succ(200,"操作成功",user);
    }

    @PostMapping("/save")
    public  Result save(@Validated @RequestBody User user){//@Validated 进行校验
        return Result.succ(user);
    }

}
