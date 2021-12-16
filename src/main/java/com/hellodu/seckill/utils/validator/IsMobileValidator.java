package com.hellodu.seckill.utils.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号码校验规则
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean isRequired = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        isRequired = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(isRequired) { // 必填
            return MobileValidatorUtils.isMobile(s);
        } else { // 非必填
            if(StringUtils.isEmpty(s)){
                return true;
            } else {
                return MobileValidatorUtils.isMobile(s);
            }
        }
    }
}
