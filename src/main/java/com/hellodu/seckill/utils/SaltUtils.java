package com.hellodu.seckill.utils;

import java.util.Random;

//随机盐生成工具类
public class SaltUtils {
    public static String getsalt(){
        char[] chars="bakfbaowbfjkabofbaCCUVUIVIWDKV%$&^%*&*%$1131415".toCharArray(); //转换为字符数组
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<5;i++){
            char ch=chars[new Random().nextInt(chars.length)];
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }
}