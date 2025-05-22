package com.travel.common.exce;


import com.travel.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理类
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        System.out.println("e.getMessage() = " + e.getMessage());
        return Result.fail();
    }

    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public Result error(GlobalException e){
        e.printStackTrace();
        return Result.fail(e.getCode(), e.getMessage());
    }
}