package com.jsal.jeduApi.student.service;

import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.repository.projection.StudentProfile;
import com.jsal.jeduApi.student.repository.projection.StudentTitle;

import java.util.List;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
public interface StudentApiService {
    /**
     * Validate unique fields {@code displayId} & {@code id}.
     * If valid then create, else then throw exception.
     *
     * @param request       CreateStudentRequest
     * @param currentUserId UUID
     */
    void create(CreateStudentRequest request, UUID currentUserId);

    /**
     * @return List of StudentTitle
     */
    List<StudentTitle> findAllTitles();

    /**
     * Find by {@code id}.
     * If exists then return, else then throw exception.
     *
     * @param id UUID
     * @return StudentProfile
     * @throws com.jsal.jeduApi.common.exception.ResourceNotFoundException if {@code id} is not found
     */
    StudentProfile findProfileById(UUID id);
}
