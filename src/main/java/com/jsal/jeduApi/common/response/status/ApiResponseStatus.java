package com.jsal.jeduApi.common.response.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Status: {@code SUCCESS} | {@code INVALID} | {@code ERROR}
 *
 * @author Yozakura Minato
 */
@RequiredArgsConstructor
@Getter
public enum ApiResponseStatus {
    /**
     * Meaning: The request is proceeded successfully
     */
    SUCCESS("SUCCESS"),

    /**
     * Meaning: The request process failed
     */
    FAIL("FAIL"),

    /**
     * Meaning: Unexpected error occurred when processing the request
     */
    ERROR("ERROR");

    private final String description;
}
