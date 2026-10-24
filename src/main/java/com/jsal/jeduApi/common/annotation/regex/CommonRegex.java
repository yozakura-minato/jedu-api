package com.jsal.jeduApi.common.annotation.regex;

import com.jsal.jeduApi.common.exception.UtilityClassInstantiationException;

/**
 * Important: DO NOT instantiate or extend this class
 *
 * @author Yozakura Minato
 */
public final class CommonRegex {
    private CommonRegex() {
        throw new UtilityClassInstantiationException();
    }

    public static final String EMAIL_REGEX = "^\\S+@\\S+\\.\\S+$";
    public static final String PHONE_NUMBER_REGEX = "^0[1-9]\\d{8}$";
}
