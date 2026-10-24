package com.jsal.jeduApi.common.exception;

/**
 * @author Yozakura Minato
 */
public final class BusinessLogicException extends RuntimeException {
    public BusinessLogicException(String message) {
        super(message, null, false, false);
    }
}
