package com.jsal.jeduApi.common.annotation.validator;

import com.jsal.jeduApi.common.annotation.TrimmedString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author Yozakura Minato
 */
public class TrimmedStringValidator implements ConstraintValidator<TrimmedString, String> {
    /**
     * @param value   String
     * @param context ConstraintValidatorContext
     * @return boolean
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return !value.isBlank() && value.equals(value.strip());
    }
}
