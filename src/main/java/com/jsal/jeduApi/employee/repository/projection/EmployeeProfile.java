package com.jsal.jeduApi.employee.repository.projection;

import com.jsal.jeduApi.employee.repository.type.EmployeeStatus;

import java.util.UUID;

/**
 * @param id          UUID
 * @param displayId   String
 * @param fullName    String
 * @param email       String
 * @param phoneNumber String
 * @param status      EmployeeStatus
 * @param department  String: Department name
 * @author Yozakura Minato
 */
public record EmployeeProfile(
        UUID id,
        String displayId,
        String fullName,
        String email,
        String phoneNumber,
        EmployeeStatus status,
        String department
) {
    /**
     * @param id UUID
     * @return EmployeeProfile
     */
    public static EmployeeProfile mockWithId(UUID id) {
        return new EmployeeProfile(
                id,
                "EM01",
                "Employee One",
                "employee-one@jedu.com",
                null,
                EmployeeStatus.ACTIVE,
                "Department One"
        );
    }
}
