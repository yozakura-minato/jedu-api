package com.jsal.jeduApi.student.service.impl;

import com.jsal.jeduApi.common.exception.ResourceConflictException;
import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
import com.jsal.jeduApi.common.exception.SqlExecutionException;
import com.jsal.jeduApi.common.util.CommonMessage;
import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.controller.response.StudentDisplayIdResponse;
import com.jsal.jeduApi.student.repository.StudentRepository;
import com.jsal.jeduApi.student.repository.projection.StudentProfile;
import com.jsal.jeduApi.student.repository.projection.StudentUniqueValidation;
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
    public StudentDisplayIdResponse create(CreateStudentRequest createRequest, UUID currentUserId) {
        // Validate unique constraints
        StudentUniqueValidation uniqueValidation = studentRepository.existsByDisplayIdOrEmail(
                createRequest.displayId(), createRequest.email()
        ).orElseThrow(() -> new SqlExecutionException(CommonMessage.SqlExecution.SELECT_FAILED));
        if (uniqueValidation.existsByDisplayId()) {
            throw new ResourceConflictException(StudentMessage.DisplayId.CONFLICT);
        }
        if (uniqueValidation.existsByEmail()) {
            throw new ResourceConflictException(StudentMessage.Email.CONFLICT);
        }

        // Create & return
        boolean isSucceed = studentRepository.insert(createRequest, currentUserId);
        if (!isSucceed) {
            throw new SqlExecutionException(CommonMessage.SqlExecution.INSERT_FAILED);
        }
        return new StudentDisplayIdResponse(createRequest.displayId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentProfile> findAllProfiles() {
        return studentRepository.selectAllProfiles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentProfile findProfileByDisplayId(String displayId) {
        return studentRepository.selectProfileByDisplayId(displayId)
                .orElseThrow(() -> new ResourceNotFoundException(StudentMessage.DisplayId.NOT_FOUND));
    }
}
