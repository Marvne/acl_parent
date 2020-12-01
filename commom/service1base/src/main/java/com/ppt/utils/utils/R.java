package com.ppt.utils.utils;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 定义controller都统一返回结果,角色,用户菜单的增删改查操作,需要返回页面到菜单显示
 * 通过R对象,让每个返回的对象都是统一的格式  返回R对象,调用ok(){}或者error(){}表示成功或者失败
 * @param: * @param: null
 * @return:
 * @author yzp
 * @date: 2020/11/28 19:30
 */
@Data
public class R {

    private Boolean success;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<String, Object>();

    //把构造方法私有
    private R() {}

    //成功静态方法
    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(20000);
        r.setMessage("成功");
        return r;
    }

    //失败静态方法
    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(20001);
        r.setMessage("失败");
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
