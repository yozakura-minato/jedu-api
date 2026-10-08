package com.jsal.jeduApi.common.response;

import com.jsal.jeduApi.common.response.status.ApiResponseStatus;

/**
 * Base body for API response
 *
 * @param status  ApiResponseStatus: Response status
 * @param data    {@code <T>}: Response data
 * @param message String: Response message
 * @param <T>     type of {@code data}
 * @author Yozakura Minato
 */
public record ApiResponse<T>(
        ApiResponseStatus status,
        T data,
        String message
) {
    /**
     * Use when the request is processed successfully
     *
     * @param data {@code <T>}: Response data
     * @param <T>  type of {@code data}
     * @return ApiResponse with {@code SUCCESS} status, {@code data} and {@code null} message
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ApiResponseStatus.SUCCESS, data, null);
    }

    /**
     * Use when the request process failed
     *
     * @param message String: Response message
     * @param <T>     type of {@code data}
     * @return ApiResponse with {@code FAIL} status, {@code null} data and {@code message}
     */
    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(ApiResponseStatus.FAIL, null, message);
    }

    /**
     * Use when unexpected error occurred when processing the request
     *
     * @param message String: Response message
     * @param <T>     type of {@code data}
     * @return ApiResponse with {@code ERROR} status, {@code null} data and {@code message}
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(ApiResponseStatus.ERROR, null, message);
    }
}
