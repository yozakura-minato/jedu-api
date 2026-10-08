package com.jsal.jeduApi.student.repository.mapper;

import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.repository.type.StudentStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Yozakura Minato
 */
@Component
public class StudentParamsMapper {
    /**
     * Create MapSqlParameterSource for student with CREATED status
     *
     * @param createRequest CreateStudentRequest
     * @param currentUserId UUID: createdBy
     * @return MapSqlParameterSource
     */
    public MapSqlParameterSource createRequestToInsertParams(CreateStudentRequest createRequest, UUID currentUserId) {
        return new MapSqlParameterSource()
                .addValue("displayId", createRequest.displayId())
                .addValue("fullName", createRequest.fullName())
                .addValue("email", createRequest.email())
                .addValue("phoneNumber", createRequest.phoneNumber())
                .addValue("hashedPassword", createRequest.password())
                .addValue("createdBy", currentUserId)
                // Addition
                .addValue("status", StudentStatus.CREATED.getDescription());
    }

    /**
     * @param displayId String
     * @param email     String
     * @return MapSqlParameterSource
     */
    public MapSqlParameterSource displayIdAndEmailToSelectParams(String displayId, String email) {
        return new MapSqlParameterSource()
                .addValue("displayId", displayId)
                .addValue("email", email);
    }

    /**
     * @param displayId String
     * @return MapSqlParameterSource
     */
    public MapSqlParameterSource displayIdToSelectParam(String displayId) {
        return new MapSqlParameterSource().addValue("displayId", displayId);
    }
}
