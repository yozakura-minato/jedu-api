package com.jsal.jeduApi.employee.repository.projection;

import com.jsal.jeduApi.employee.repository.type.EmployeeStatus;

import java.util.List;
import java.util.UUID;

/**
 * @param id          UUID
 * @param displayId   String
 * @param fullName    String
 * @param email       String
 * @param phoneNumber String
 * @param status      EmployeeStatus
 * @param departments List of department name
 * @author Yozakura Minato
 */
public record EmployeeProfile(
        UUID id,
        String displayId,
        String fullName,
        String email,
        String phoneNumber,
        EmployeeStatus status,
        List<String> departments
) {
    /**
     * @param id UUID
     * @return EmployeeProfile
     */
    public static EmployeeProfile mockWithId(UUID id) {
        return new EmployeeProfile(
                id,
                "id4tester",
                "JEDU Tester",
                "tester@jedu.dev",
                null,
                EmployeeStatus.ACTIVE,
                List.of("Department 1", "Department 2", "Department 3")
        );
    }
}
