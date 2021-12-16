package com.hellodu.seckill.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // 类中的属性名称，不是表的字段名称
        this.setFieldValByName("createTime", new Date(), metaObject);
        String nickName = UUID.randomUUID().toString().replace("-", "").substring(0, 7);
        this.setFieldValByName("nickname", nickName, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
