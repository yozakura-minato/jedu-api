package com.jsal.jeduApi.common.response;

/**
 * Base body for API response
 *
 * @param data  T: Response data when request success
 * @param error String: Error message when request failed
 * @param <T>   type of {@code data}
 * @author Yozakura Minato
 */
public record ApiResponse<T>(
        T data,
        String error
) {
    /**
     * Use when request success
     *
     * @param data T: Response data
     * @param <T>  type of {@code data}
     * @return ApiResponse with {@code data} and null {@code error}
     */
    public static <T> ApiResponse<T> data(T data) {
        return new ApiResponse<>(data, null);
    }

    /**
     * Use when request failed
     *
     * @param error String: Error message
     * @param <T>   type of {@code data}
     * @return ApiResponse with {@code error} and null {@code data}
     */
    public static <T> ApiResponse<T> error(String error) {
        return new ApiResponse<>(null, error);
    }
}
