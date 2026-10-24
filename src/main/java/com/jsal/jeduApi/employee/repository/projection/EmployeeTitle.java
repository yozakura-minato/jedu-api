package com.jsal.jeduApi.employee.repository.projection;

import java.util.List;
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
    /**
     * @return List of EmployeeTitle
     */
    public static List<EmployeeTitle> mockList() {
        return List.of(
                new EmployeeTitle(UUID.randomUUID(), "EM01", "Employee One"),
                new EmployeeTitle(UUID.randomUUID(), "EM02", "Employee Two"),
                new EmployeeTitle(UUID.randomUUID(), "EM03", "Employee Three")
        );
    }
}
