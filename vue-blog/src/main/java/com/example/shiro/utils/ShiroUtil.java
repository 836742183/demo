package com.example.shiro.utils;

import com.example.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @Author: 王梓吉
 * @Date: 2020/12/13 13:25
 * @Description: TODO
 **/

public class ShiroUtil {
    public static AccountProfile getProfile(){
        //封装获取profile的方法
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
