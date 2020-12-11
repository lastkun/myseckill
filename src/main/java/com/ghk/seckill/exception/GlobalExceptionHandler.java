package com.ghk.seckill.exception;

import com.ghk.seckill.result.CodeMsg;
import com.ghk.seckill.result.Result;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;




@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(Exception e){
        e.printStackTrace();//先打印异常信息
        if (e instanceof BusinessException){
            return Result.fail(((BusinessException) e).getCodeMsg());
        } else if (e instanceof BindException){
            //BindException be = (BindException) e;
            return Result.fail(CodeMsg.BIND_EXCEPTION);
        } else{
            return Result.fail(CodeMsg.ERROR_SERVER);
        }
    }
}
