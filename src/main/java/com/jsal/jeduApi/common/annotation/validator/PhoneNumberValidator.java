package com.jsal.jeduApi.common.annotation.validator;

import com.jsal.jeduApi.common.annotation.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * @author Yozakura Minato
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    /**
     * @param value   String
     * @param context ConstraintValidatorContext
     * @return boolean
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || Pattern.compile("^0\\d{9}$").matcher(value).matches();
    }
}
