package com.example.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.lang.Result;
import com.example.entity.Blog;
import com.example.service.BlogService;
import com.example.shiro.utils.ShiroUtil;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @Author: 王梓吉
 * @Date: 2020/12/12 17:05
 * @Description: 博客功能接口开发
 **/
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){//@RequestParam给默认值

        //分页
        Page page =new Page(currentPage,5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.succ(pageData);

    }

    @GetMapping("/blogs/{id}")
    public Result detail(@PathVariable(name = "id") Long id){

        Blog blog =blogService.getById(id);
        Assert.notNull(blog,"该博客已被删除");

        return Result.succ(blog);

    }

    @RequiresAuthentication//需要登录了才能编辑
    @GetMapping("/blogs/edit")
    public Result edit(@Validated @RequestBody Blog blog){
        //添改一体，有id修改，没id添加

        Blog temp =null;

        if (blog.getId()!=null){//编辑
            temp =blogService.getById(blog.getId());
            //只能编辑自己的文章
            Assert.isTrue(temp.getUserId()==ShiroUtil.getProfile().getId().longValue(),"没有权限编辑");

        }else {//添加
            temp= new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        BeanUtil.copyProperties(blog,temp,"id","userId","created","status");
        //blog→temp

        blogService.saveOrUpdate(temp);

        return Result.succ(null);

    }
}
