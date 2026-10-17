package com.jsal.jeduApi.common.annotation;

import com.jsal.jeduApi.common.annotation.validator.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Check if a string is null
 * or has format likes `xxx@yyy.zzz` (x, y,z is not whitespace).
 *
 * @author Yozakura Minato
 */
@Constraint(validatedBy = EmailValidator.class)
@Target(value = {ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Email {
    String message() default "Invalid email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
