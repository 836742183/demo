package com.example.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description: TODO
 * @Author: 王梓吉
 * @Date: 2020/12/11 22:00
 * @Version: 1.0
 **/
    @Configuration
    @EnableTransactionManagement
    @MapperScan("com.example.mapper")
    public class MybatisPlusConfig {
        @Bean
        public PaginationInterceptor paginationInterceptor() {
            PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
            return paginationInterceptor;
        }
    }
