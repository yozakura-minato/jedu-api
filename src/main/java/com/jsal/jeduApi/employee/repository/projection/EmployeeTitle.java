package com.jsal.jeduApi.employee.repository.projection;

import java.util.UUID;

/**
 * @param id        UUID
 * @param displayId String
 * @param fullName  String
 * @author Yozakura Minato
 */
public record EmployeeTitle(
        UUID id,
        String displayId,
        String fullName
) {
}
