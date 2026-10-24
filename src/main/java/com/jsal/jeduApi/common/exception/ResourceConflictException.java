package com.jsal.jeduApi.common.exception;

/**
 * @author Yozakura Minato
 */
public final class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message, null, false, false);
    }
}
