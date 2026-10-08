package com.jsal.jeduApi.student.repository.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Status: {@code CREATED} | {@code ACTIVE}
 *
 * @author Yozakura Minato
 */
@RequiredArgsConstructor
@Getter
public enum StudentStatus {
    CREATED("CREATED"),
    ACTIVE("ACTIVE");

    private final String description;
}
