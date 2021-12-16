package com.hellodu.seckill.utils.exceptionhandler;


public class MyExceptionHandler extends RuntimeException {
    private Integer code;

    private String msg; // 异常信息

    public MyExceptionHandler() {
    }

    public MyExceptionHandler(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
