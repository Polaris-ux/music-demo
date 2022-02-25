package com.mjy.musicdemo.util;

import java.util.*;

/**
 * @author
 * @description
 * @2022-02-01 16:54
 */
public class ResultInfo{
    // 返回状态码
    private String code;
    // 返回信息
    private String message;
    // 返回结果集
    private Map<String,Object> result=new HashMap<>();


    public ResultInfo(String code, String message){
        this.code = code;
        this.message = message;
    }
    public ResultInfo(String code, String message,Map<String,Object> result){
        this.code = code;
        this.message = message;
        this.result = result;
    }
    public ResultInfo(){

    }

    public ResultInfo setCode(String code){
        this.code=code;
        return this;
    }

    public ResultInfo setMessage(String message){
        this.message=message;
        return this;
    }

    public ResultInfo setResult(Map<String,Object> result){
        this.result=result;
        return this;
    }

    public ResultInfo setData(String key,Object data){
        this.result.put(key,data);
        return this;
    }

    public static ResultInfo ok(){
        return ok(null);
    }

    public static ResultInfo ok(String message){
        ResultInfo resultInfo = new ResultInfo(Consts.SUCCESS_CODE, message);
        return resultInfo;
    }

    public static ResultInfo error(String code,String message){
        return new ResultInfo(code,message);
    }
    public static ResultInfo error(){
        return new ResultInfo();
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getResult() {
        return result;
    }
}
