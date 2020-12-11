package com.ghk.seckill.validator;

import com.ghk.seckill.result.CheckUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class CheckMobileValidator implements ConstraintValidator<CheckMobile,String> {

    private boolean required = false;
    /**
     * 拿到注解
     * @param constraintAnnotation
     */
    @Override
    public void initialize(CheckMobile constraintAnnotation) {
        required =constraintAnnotation.required();
    }

    /**
     * 校验逻辑
     * @param s
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return CheckUtil.checkMobile(s);
        }else {
            if (StringUtils.isEmpty(s))
                return true;
            else
                return CheckUtil.checkMobile(s);
        }
    }
}
