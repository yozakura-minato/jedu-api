package com.jsal.jeduApi.student.repository.type;

/**
 * Status: {@code CREATED} | {@code ACTIVE}
 *
 * @author Yozakura Minato
 */
public enum StudentStatus {
    CREATED("CREATED"),
    ACTIVE("ACTIVE");

    private final String description;

    StudentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
