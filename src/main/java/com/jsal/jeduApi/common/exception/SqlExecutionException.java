package com.jsal.jeduApi.common.exception;

/**
 * @author Yozakura Minato
 */
public final class SqlExecutionException extends RuntimeException {
    private static final String UNEXPECTED_RESULT_LOCALIZED_MESSAGE = "SQL execution returned unexpected result";
    private static final String UNEXPECTED_EFFECT_LOCALIZED_MESSAGE = "SQL execution has unexpected effects on rows";

    private SqlExecutionException(String localizedMessage) {
        super(localizedMessage, null, false, false);
    }

    /**
     * Use case: SQL execution returned unexpected result
     *
     * @return SqlExecutionException with localized message
     */
    public static SqlExecutionException unexpectedResult() {
        return new SqlExecutionException(UNEXPECTED_RESULT_LOCALIZED_MESSAGE);
    }

    /**
     * Use case: SQL execution has unexpected effects on rows
     *
     * @return SqlExecutionException with localized message
     */
    public static SqlExecutionException unexpectedEffect() {
        return new SqlExecutionException(UNEXPECTED_EFFECT_LOCALIZED_MESSAGE);
    }
}
