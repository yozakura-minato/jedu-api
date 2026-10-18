package com.jsal.jeduApi.common.annotation.validator;

import com.jsal.jeduApi.common.annotation.StrongPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * @author Yozakura Minato
 */
public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    private static final Pattern UPPERCASE_LETTER_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern LOWERCASE_LETTER_PATTERN = Pattern.compile(".*[a-z].*");
    private static final Pattern NUMBER_DIGIT_PATTERN = Pattern.compile(".*[0-9].*");
    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile(".*[^A-Za-z0-9].*");

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

        if (value.length() < 8) {
            return false;
        }

        int strengthLevel = 0;
        if (UPPERCASE_LETTER_PATTERN.matcher(value).matches()) {
            strengthLevel++;
        }
        if (LOWERCASE_LETTER_PATTERN.matcher(value).matches()) {
            strengthLevel++;
        }
        if (NUMBER_DIGIT_PATTERN.matcher(value).matches()) {
            strengthLevel++;
        }
        if (SPECIAL_CHARACTER_PATTERN.matcher(value).matches()) {
            strengthLevel++;
        }
        return strengthLevel >= 2;
    }
}
