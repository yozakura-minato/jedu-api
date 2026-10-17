package com.jsal.jeduApi.common.annotation;

import com.jsal.jeduApi.common.annotation.validator.PhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Check if a string is null
 * or contains 10 number digits and start with 0.
 *
 * @author Yozakura Minato
 */
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target(value = {ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface PhoneNumber {
    String message() default "Invalid phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
