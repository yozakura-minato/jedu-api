package com.jsal.jeduApi.student.service.impl;

import com.jsal.jeduApi.common.exception.ResourceConflictException;
import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.controller.request.UpdateStudentRequest;
import com.jsal.jeduApi.student.repository.StudentRepository;
import com.jsal.jeduApi.student.repository.projection.StudentDisplayTitle;
import com.jsal.jeduApi.student.repository.projection.StudentProfile;
import com.jsal.jeduApi.student.repository.projection.StudentUniqueValidation;
import com.jsal.jeduApi.student.repository.type.StudentStatus;
import com.jsal.jeduApi.student.service.StudentApiService;
import com.jsal.jeduApi.student.util.StudentMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
@Service
public class StudentServiceImpl implements StudentApiService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public UUID createStudent(CreateStudentRequest createRequest, UUID currentUserId) {
        StudentUniqueValidation studentUniqueValidation = studentRepository
                .existsStudentByDisplayIdOrEmail(createRequest.displayId(), createRequest.email());
        if (studentUniqueValidation.existsByDisplayId()) {
            throw new ResourceConflictException(StudentMessage.DisplayId.CONFLICT);
        }
        if (studentUniqueValidation.existsByEmail()) {
            throw new ResourceConflictException(StudentMessage.Email.CONFLICT);
        }

        return studentRepository.insertStudent(createRequest, StudentStatus.CREATED, currentUserId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentProfile> findAllStudents() {
        return studentRepository.selectAllStudents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentProfile findStudentById(UUID id) {
        return studentRepository.selectStudentById(id)
                .orElseThrow(() -> new ResourceNotFoundException(StudentMessage.Id.NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updateStudent(UUID id, UpdateStudentRequest request, UUID currentUserId) {
        boolean existsStudentById = studentRepository.existsStudentById(id);
        if (!existsStudentById) {
            throw new ResourceNotFoundException(StudentMessage.Id.NOT_FOUND);
        }

        boolean conflictStudentById = studentRepository.existsStudentByDisplayAndIdIsNot(request.displayId(), id);
        if (conflictStudentById) {
            throw new ResourceConflictException(StudentMessage.DisplayId.CONFLICT);
        }

        studentRepository.updateStudent(id, request, currentUserId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public StudentDisplayTitle softDeleteStudent(UUID studentId, UUID currentUserId) {
        return studentRepository.softDeleteStudent(studentId, currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException(StudentMessage.Id.NOT_FOUND));
    }
}
