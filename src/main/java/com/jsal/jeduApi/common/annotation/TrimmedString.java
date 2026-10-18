package com.jsal.jeduApi.common.annotation;

import com.jsal.jeduApi.common.annotation.validator.TrimmedStringValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Check if a String is null
 * or is trimmed not blank
 *
 * @author Yozakura Minato
 */
@Constraint(validatedBy = TrimmedStringValidator.class)
@Target(value = {ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface TrimmedString {
    String message() default "Not trimmed string";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
