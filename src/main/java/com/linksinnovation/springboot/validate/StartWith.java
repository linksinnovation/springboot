package com.linksinnovation.springboot.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartWithConstraintValidator.class)
public @interface StartWith {
    
    public String value() default "";
    
    public String message() default "Start with error";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
