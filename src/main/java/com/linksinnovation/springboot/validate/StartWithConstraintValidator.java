package com.linksinnovation.springboot.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
public class StartWithConstraintValidator implements ConstraintValidator<StartWith, String>{
    
    private String value;

    @Override
    public void initialize(StartWith a) {
        this.value = a.value();
    }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        if(t == null){
            return false;
        }
        return t.startsWith(value);
    }
    
}
