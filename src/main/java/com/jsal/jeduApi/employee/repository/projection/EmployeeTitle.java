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
    public static List<EmployeeTitle> listMock() {
        return List.of(
                new EmployeeTitle(UUID.randomUUID(), "ID01", "Tester 01"),
                new EmployeeTitle(UUID.randomUUID(), "ID02", "Tester 02"),
                new EmployeeTitle(UUID.randomUUID(), "ID03", "Tester 03")
        );
    }
}
