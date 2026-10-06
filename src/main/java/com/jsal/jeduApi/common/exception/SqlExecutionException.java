package com.jsal.jeduApi.common.exception;

/**
 * @author Yozakura Minato
 */
public class SqlExecutionException extends RuntimeException {
    public SqlExecutionException(String message) {
        super(message, null, false, false);
    }
}
