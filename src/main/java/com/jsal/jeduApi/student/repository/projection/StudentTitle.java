package com.jsal.jeduApi.student.repository.projection;


import java.util.List;
import java.util.UUID;

/**
 *
 * @param id        UUID
 * @param displayId String
 * @param fullName  String
 * @author Yozakura Minato
 */
public record StudentTitle(
        UUID id,
        String displayId,
        String fullName
) {
    /**
     * @return StudentTitle
     */
    public static StudentTitle mock() {
        return new StudentTitle(UUID.randomUUID(), "ST01", "Student One");
    }

    /**
     * @return List of StudentTitle
     */
    public static List<StudentTitle> mockList() {
        return List.of(
                new StudentTitle(UUID.randomUUID(), "ST01", "Student One"),
                new StudentTitle(UUID.randomUUID(), "ST02", "Student Two"),
                new StudentTitle(UUID.randomUUID(), "ST03", "Student Three")
        );
    }
}
