package com.jsal.jeduApi.common.exception;

/**
 * @author Yozakura Minato
 */
public final class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message, null, false, false);
    }
}
