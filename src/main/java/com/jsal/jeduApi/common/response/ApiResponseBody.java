package com.jsal.jeduApi.common.response;

/**
 * @param data    {@code T}: Response data
 * @param message String: Error message
 * @param <T>     type of {@code data}
 * @author Yozakura Minato
 */
public record ApiResponseBody<T>(
        T data,
        String message
) {
    /**
     * @param data {@code T}
     * @param <T>  type of {@code data}
     * @return ApiResponseBody with {@code data} and {@code null} message
     */
    public static <T> ApiResponseBody<T> data(T data) {
        return new ApiResponseBody<>(data, null);
    }

    /**
     * @param message String
     * @param <T>     type of {@code data}
     * @return ApiResponseBody with {@code null} data and {@code message}
     */
    public static <T> ApiResponseBody<T> message(String message) {
        return new ApiResponseBody<>(null, message);
    }

    /**
     * @param <T> type of {@code data}
     * @return ApiResponseBody with {@code null} data and {@code null} message
     */
    public static <T> ApiResponseBody<T> empty() {
        return new ApiResponseBody<>(null, null);
    }
}
