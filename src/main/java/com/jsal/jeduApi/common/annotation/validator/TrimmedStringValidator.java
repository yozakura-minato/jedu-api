package com.jsal.jeduApi.common.annotation.validator;

import com.jsal.jeduApi.common.annotation.TrimmedString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author Yozakura Minato
 */
public class TrimmedStringValidator implements ConstraintValidator<TrimmedString, String> {
    /**
     * @param value   String to validate
     * @param context context in which the constraint is evaluated
     * @return true if {@code value} is null or is trimmed not blank String, or else false
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return !value.isBlank() && value.equals(value.strip());
    }
}
