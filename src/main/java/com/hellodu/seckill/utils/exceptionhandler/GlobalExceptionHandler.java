package com.hellodu.seckill.utils.exceptionhandler;


import com.hellodu.seckill.utils.R;
import com.hellodu.seckill.utils.ResultCode;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // 指定出现了什么异常才会执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        if(e instanceof BindException) {
            BindException ex = (BindException) e;
            R r = R.error();
            r.setCode(ResultCode.BIND_ERROR);
            return r.message("参数校验异常:" + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        }
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }



    // 特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();;
        return R.error().message("执行了ArithmeticException异常处理");
    }

    // 自定义异常
    @ExceptionHandler(MyExceptionHandler.class)
    @ResponseBody
    public R error(MyExceptionHandler e){
        e.printStackTrace();;
        return R.error().code(e.getCode()).message(e.getMsg());  // 测试时，必须手动抛出异常 使用try-catch
    }
}
