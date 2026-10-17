package com.jsal.jeduApi.common.annotation;

import com.jsal.jeduApi.common.annotation.validator.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Check if a String is null
 * or contains at least 8 characters
 * and 2 types of uppercase/lowercase letter, number digit, special character
 *
 * @author Yozakura Minato
 */
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target(value = {ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface StrongPassword {
    String message() default "Weak password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
