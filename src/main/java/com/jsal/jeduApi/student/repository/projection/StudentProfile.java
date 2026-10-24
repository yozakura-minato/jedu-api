package com.jsal.jeduApi.student.repository.projection;

import com.jsal.jeduApi.student.repository.type.StudentStatus;

import java.util.UUID;

/**
 * @param id          UUDI
 * @param displayId   String
 * @param fullName    String
 * @param email       String
 * @param phoneNumber String
 * @param status      StudentStatus
 * @author Yozakura Minato
 */
public record StudentProfile(
        UUID id,
        String displayId,
        String fullName,
        String email,
        String phoneNumber,
        StudentStatus status
) {
    /**
     * @return StudentProfile
     */
    public static StudentProfile mockWithId(UUID studentId) {
        return new StudentProfile(
                studentId,
                "ST01",
                "Student One",
                "student-one@jedu.com",
                null,
                StudentStatus.CREATED
        );
    }
}
