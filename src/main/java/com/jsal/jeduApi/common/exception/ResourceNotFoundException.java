package com.jsal.jeduApi.common.exception;

/**
 * @author Yozakura Minato
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message, null, false, false);
    }
}
