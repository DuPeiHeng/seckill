package com.hellodu.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 前端 ： Password = MD5(用户输入明文  + 固定salt)
 * 后端：  Password = MD5(前端传输结果 + 随机salt)  保存到数据库
 */
public class MD5 {

    /**
     * md5加密结果
     * @param src 输入参数
     * @return
     */
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }


    public static void main(String[] args) {
        String str = "123";
        System.out.println(md5(str));
    }
}
