package com.jsal.jeduApi.student.repository.projection;

import com.jsal.jeduApi.student.repository.type.StudentStatus;

/**
 * @param displayId   String
 * @param fullName    String
 * @param email       String
 * @param phoneNumber String
 * @param status      StudentStatus
 * @author Yozakura Minato
 */
public record StudentProfile(
        String displayId,
        String fullName,
        String email,
        String phoneNumber,
        StudentStatus status
) {
}
