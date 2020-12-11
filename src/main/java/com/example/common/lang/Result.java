package com.example.common.lang;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author: 王梓吉
 * @Date: 2020/12/12 1:03
 * @Version: 1.0
 **/
@Data
public class Result implements Serializable {//序列化

    private int code;//200是正常数据，非200是异常数据
    private String msg;
    private Object data;

    public static Result succ(Object data){
        return succ(200,"操作成功",data);
    }

    public static Result succ(int code,String msg,Object data){
        /**
         * @Author: 王梓吉
         * @Description: 构造函数
         * @Date: 2020/12/12 1:06
         * @param: data
         * @Return: Result
         **/
        Result result=new Result() ;
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);

        return result;
    }

    public static Result fail(int code, String msg,Object data){
        Result result=new Result() ;
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result fail(String msg, Object data){
        return fail(400,msg,data);
    }
}
