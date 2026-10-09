package com.jsal.jeduApi.common.exception;

/**
 * @author Yozakura Minato
 */
public class SqlExecutionException extends RuntimeException {
    public SqlExecutionException(String localizedMessage) {
        super(localizedMessage, null, false, false);
    }

    /**
     * Use case: SQL execution returned unexpected result
     *
     * @return SqlExecutionException with localized message
     */
    public static SqlExecutionException unexpectedResult() {
        return new SqlExecutionException("SQL execution returned unexpected result");
    }

    /**
     * Use case: SQL execution has unexpected effects on rows
     *
     * @return SqlExecutionException with localized message
     */
    public static SqlExecutionException unexpectedEffect() {
        return new SqlExecutionException("SQL execution has unexpected effects on rows");
    }
}
