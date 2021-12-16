package com.hellodu.seckill.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 验证手机号格式
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = { IsMobileValidator.class }
)
public @interface IsMobile {

    boolean required() default true; // 手机号码是必填的

    String message() default "{手机号码格式错误}"; // 校验失败的提示

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
