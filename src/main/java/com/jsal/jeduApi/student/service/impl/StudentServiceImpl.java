package com.jsal.jeduApi.student.service.impl;

import com.jsal.jeduApi.common.exception.ResourceConflictException;
import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.repository.StudentRepository;
import com.jsal.jeduApi.student.repository.projection.StudentProfile;
import com.jsal.jeduApi.student.repository.projection.StudentTitle;
import com.jsal.jeduApi.student.repository.projection.StudentUniqueValidation;
import com.jsal.jeduApi.student.repository.type.StudentStatus;
import com.jsal.jeduApi.student.service.StudentApiService;
import com.jsal.jeduApi.student.util.StudentMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentApiService {
    private final StudentRepository studentRepository;

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
    public List<StudentTitle> findAllTitles() {
        return studentRepository.selectAllStudentTitles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentProfile findStudentProfileById(UUID id) {
        return studentRepository.selectStudentProfileById(id)
                .orElseThrow(() -> new ResourceNotFoundException(StudentMessage.Id.NOT_FOUND));
    }
}
