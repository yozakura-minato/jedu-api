package com.jsal.jeduApi.student.service;

import com.jsal.jeduApi.common.exception.ResourceConflictException;
import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.controller.request.UpdateStudentRequest;
import com.jsal.jeduApi.student.repository.projection.StudentProfile;
import com.jsal.jeduApi.student.repository.projection.StudentTitle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
public interface StudentApiService {
    /**
     * <ol>
     *     <li>If {@code request.displayId} or {@code request.email} exists, then throw exception</li>
     *     <li>Create and return</li>
     * </ol>
     *
     * @param request       CreateStudentRequest
     * @param currentUserId UUID
     * @return Created student ID
     */
    UUID createStudent(CreateStudentRequest request, UUID currentUserId);

    /**
     * @return Student profile list
     */
    List<StudentTitle> findAllTitles();

    /**
     * <ol>
     *     <li>If {@code studentId} not exist, then throw exception</li>
     *     <li>Return</li>
     * </ol>
     *
     * @param studentId UUID
     * @return Student profile
     * @throws ResourceNotFoundException if {@code id} is not found
     */
    StudentProfile findStudentProfileById(UUID studentId);

    /**
     * <ol>
     *     <li>If {@code studentId} not exist then throw exception</li>
     *     <li>If {@code request.displayId} conflicts then throw exception</li>
     *     <li>Update & return</li>
     * </ol>
     *
     * @param studentId     UUID
     * @param request       UpdateStudentRequest
     * @param currentUserId UUID
     * @return Student updated time
     * @throws ResourceNotFoundException if {@code id} not exists
     * @throws ResourceConflictException if {@code displayId} conflict
     */
    LocalDateTime updateStudent(UUID studentId, UpdateStudentRequest request, UUID currentUserId);

    /**
     * <ol>
     *     <li>If {@code studentId} is not found, then throw exception</li>
     *     <li>Soft delete and return</li>
     * </ol>
     *
     * @param studentId     UUID
     * @param currentUserId UUID
     * @return Student soft deleted time
     */
    LocalDateTime softDeleteStudent(UUID studentId, UUID currentUserId);
}
