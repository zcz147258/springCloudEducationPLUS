package com.example.servicebase.exception;


import com.example.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j//日志
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)//捕捉异常
    @ResponseBody//返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    /**
     * 特定异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)//捕捉异常
    @ResponseBody//返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常");
    }

    /**
     * 自定义异常类
     */
    @ExceptionHandler(GuliExecption.class)//捕捉异常
    @ResponseBody//返回数据
    public R error(GuliExecption e){
        /**
         * log
         */
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}
