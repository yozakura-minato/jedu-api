package com.jsal.jeduApi.common.exception;

/**
 * @author Yozakura Minato
 */
public final class UtilityClassInstantiationException extends RuntimeException {
    private static final String LOCALIZED_MESSAGE = "Utility classes should not be instantiated";

    public UtilityClassInstantiationException() {
        super(LOCALIZED_MESSAGE, null, false, false);
    }
}
