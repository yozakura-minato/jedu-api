package com.jsal.jeduApi.common.util;

/**
 * @author Yozakura Minato
 */
public class CommonMessage {
    // For client
    public static final String BAD_REQUEST = "common.badRequest";
    public static final String INTERNAL_SERVER_ERROR = "common.internalServerError";

    public static class SqlExecution {
        // Internal
        public static final String INSERT_FAILED = "common.sqlExecution.insertFailed";
        public static final String SELECT_FAILED = "common.sqlExecution.selectFailed";
        public static final String UPDATE_FAILED = "common.sqlExecution.updateFailed";
        public static final String DELETE_FAILED = "common.sqlExecution.deleteFailed";
    }
}
