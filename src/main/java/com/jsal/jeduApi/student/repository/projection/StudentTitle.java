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
     * @return StudentTitle List
     */
    public static List<StudentTitle> mockList() {
        return List.of(
                new StudentTitle(UUID.randomUUID(), "std1", "Student 1"),
                new StudentTitle(UUID.randomUUID(), "std2", "Student 2"),
                new StudentTitle(UUID.randomUUID(), "std3", "Student 3")
        );
    }
}
