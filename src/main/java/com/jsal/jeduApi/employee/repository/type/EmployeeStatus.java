package com.jsal.jeduApi.employee.repository.type;

/**
 * Status: {@code CREATED} | {@code ACTIVE}
 *
 * @author Yozakura Minato
 */
public enum EmployeeStatus {
    CREATED("CREATED"),
    ACTIVE("ACTIVE");

    private final String description;

    EmployeeStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
