package com.hellodu.seckill.utils;

/**
 * 返回结果状态码
 */
public interface ResultCode {

    Integer SUCCESS = 20000;

    Integer ERROR = 20001;

    Integer MobileIsEmptyError = 20002;   //手机号不能为空

    Integer PwdIsEmptyError = 20003; // 密码不能为空

    Integer UsernameIsNotExist = 20004; // 用户名不存在

    Integer PwdError = 20005; //密码不正确

    Integer BIND_ERROR = 20006; // 手机号码格式不正确

    Integer NoStockError = 20007; // 库存不足

    Integer RepeatSeckillError = 20008; // 重复下单

    Integer NotLoginError = 20009; // 未登录异常

    Integer PwdUpdateError = 20010; // 密码修改失败
}
