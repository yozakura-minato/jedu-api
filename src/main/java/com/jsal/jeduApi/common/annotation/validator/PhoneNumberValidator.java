package com.jsal.jeduApi.common.annotation.validator;

import com.jsal.jeduApi.common.annotation.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * @author Yozakura Minato
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^0\\d{9}$");

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
        return PHONE_NUMBER_PATTERN.matcher(value).matches();
    }
}
