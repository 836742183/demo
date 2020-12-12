package com.example.controller;


import com.example.common.lang.Result;
import com.example.entity.Blog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 王梓吉
 * @Date: 2020/12/12 17:05
 * @Description: 博客功能接口开发
 **/
@RestController
public class BlogController {

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){//@RequestParam给默认值

        return Result.succ(null);

    }

    @GetMapping("/blogs/{id}")
    public Result detail(@PathVariable(name = "id") Long id){

        return Result.succ(null);

    }

    @GetMapping("/blogs/edit")
    public Result edit(@Validated @RequestBody Blog blog){

        return Result.succ(null);

    }
}
