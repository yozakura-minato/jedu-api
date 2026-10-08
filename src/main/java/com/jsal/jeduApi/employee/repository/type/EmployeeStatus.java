package com.jsal.jeduApi.employee.repository.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Status: {@code CREATED} | {@code ACTIVE}
 *
 * @author Yozakura Minato
 */
@RequiredArgsConstructor
@Getter
public enum EmployeeStatus {
    CREATED("CREATED"),
    ACTIVE("ACTIVE");

    private final String description;
}
