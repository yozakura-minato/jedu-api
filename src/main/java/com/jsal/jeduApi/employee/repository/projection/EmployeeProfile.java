package com.jsal.jeduApi.employee.repository.projection;

import com.jsal.jeduApi.employee.model.EmployeeStatus;

/**
 * @param displayId   String
 * @param fullName    String
 * @param email       String
 * @param phoneNumber String
 * @param status      EmployeeStatus
 * @author Yozakura Minato
 */
public record EmployeeProfile(
        String displayId,
        String fullName,
        String email,
        String phoneNumber,
        EmployeeStatus status
) {
}
