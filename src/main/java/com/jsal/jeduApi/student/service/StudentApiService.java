package com.jsal.jeduApi.student.service;

import com.jsal.jeduApi.student.controller.response.StudentDisplayIdResponse;
import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.repository.projection.StudentProfile;

import java.util.List;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
public interface StudentApiService {
    /**
     * @param request       CreateStudentRequest
     * @param currentUserId UUID
     * @return StudentDisplayIdResponse
     * @throws com.jsal.jeduApi.common.exception.ResourceConflictException if unique fields is conflicts
     */
    StudentDisplayIdResponse create(CreateStudentRequest request, UUID currentUserId);

    /**
     * @return List of StudentProfile
     */
    List<StudentProfile> findAllProfiles();

    /**
     * @param displayId String
     * @return StudentProfile
     * @throws com.jsal.jeduApi.common.exception.ResourceNotFoundException if {@code displayId} is not found
     */
    StudentProfile findProfileByDisplayId(String displayId);
}
