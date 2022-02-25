package com.mjy.musicdemo.Exception;

import com.mjy.musicdemo.util.ResultInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 * @description
 * @create 2022-02-01 17:34
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultInfo error(Exception e){
        e.printStackTrace();
        return new ResultInfo().setMessage(e.getMessage());
    }
}
