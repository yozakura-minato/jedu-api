package com.jsal.jeduApi.student.repository;

import com.jsal.jeduApi.common.exception.SqlExecutionException;
import com.jsal.jeduApi.persistence.util.CommonRowMapper;
import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.controller.request.UpdateStudentRequest;
import com.jsal.jeduApi.student.repository.projection.StudentDisplayTitle;
import com.jsal.jeduApi.student.repository.projection.StudentProfile;
import com.jsal.jeduApi.student.repository.projection.StudentTitle;
import com.jsal.jeduApi.student.repository.projection.StudentUniqueValidation;
import com.jsal.jeduApi.student.repository.type.StudentStatus;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
@Repository
public class StudentRepository {
    private static final RowMapper<StudentUniqueValidation> STUDENT_UNIQUE_VALIDATION_ROW_MAPPER
            = new DataClassRowMapper<>(StudentUniqueValidation.class);
    private static final RowMapper<StudentTitle> STUDENT_TITLE_ROW_MAPPER
            = new DataClassRowMapper<>(StudentTitle.class);
    private static final RowMapper<StudentProfile> STUDENT_PROFILE_ROW_MAPPER
            = new DataClassRowMapper<>(StudentProfile.class);
    private static final RowMapper<StudentDisplayTitle> STUDENT_DISPLAY_TITLE_ROW_MAPPER
            = new DataClassRowMapper<>(StudentDisplayTitle.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StudentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @param studentDisplayId String
     * @param studentEmail     String
     * @return StudentUniqueValidation
     * @throws SqlExecutionException Unexpected result
     */
    public StudentUniqueValidation existsStudentByDisplayIdOrEmail(String studentDisplayId, String studentEmail) {
        String sql = """
                SELECT  EXISTS (SELECT 1
                                FROM students
                                WHERE display_id = :studentDisplayId
                                AND is_deleted = false)
                            AS exists_by_display_id,
                        EXISTS (SELECT 1
                                FROM students
                                WHERE email = :studentEmail
                                AND is_deleted = FALSE)
                            AS exists_by_email;
                """;
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("studentDisplayId", studentDisplayId)
                .addValue("studentEmail", studentEmail);
        return jdbcTemplate.query(sql, param, STUDENT_UNIQUE_VALIDATION_ROW_MAPPER).stream().findFirst()
                .orElseThrow(SqlExecutionException::unexpectedResult);
    }

    /**
     * @param request       CreateStudentRequest
     * @param studentStatus StudentStatus
     * @param currentUserId UUID
     * @return Created student UUID
     * @throws SqlExecutionException unexpected result
     */
    public UUID insertStudent(CreateStudentRequest request, StudentStatus studentStatus, UUID currentUserId) {
        String sql = """
                INSERT INTO students (display_id,
                                      full_name,
                                      email,
                                      phone_number,
                                      hashed_password,
                                      status,
                                      created_at,
                                      created_by)
                VALUES (:displayId,
                        :fullName,
                        :email,
                        :phoneNumber,
                        :hashedPassword,
                        :studentStatus,
                        NOW(),
                        :currentUserId)
                RETURNING id;
                """;
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("displayId", request.displayId())
                .addValue("fullName", request.fullName())
                .addValue("email", request.email())
                .addValue("phoneNumber", request.phoneNumber())
                .addValue("hashedPassword", request.password())
                .addValue("studentStatus", studentStatus.getDescription())
                .addValue("currentUserId", currentUserId);
        return jdbcTemplate.query(sql, param, CommonRowMapper.UUID).stream().findFirst()
                .orElseThrow(SqlExecutionException::unexpectedResult);
    }

    /**
     * @return List of StudentTitle
     */
    public List<StudentTitle> selectAllStudentTitles() {
        String sql = """
                SELECT id, display_id, full_name
                FROM students
                WHERE is_deleted = FALSE;
                """;
        return jdbcTemplate.query(sql, STUDENT_TITLE_ROW_MAPPER);
    }

    /**
     * @param studentId UUID
     * @return Optional of StudentProfile
     */
    public Optional<StudentProfile> selectStudentProfileById(UUID studentId) {
        String sql = """
                SELECT id, display_id, full_name, email, phone_number, status
                FROM students
                WHERE id = :studentId
                AND is_deleted = FALSE;
                """;
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("studentId", studentId);
        return jdbcTemplate.query(sql, param, STUDENT_PROFILE_ROW_MAPPER).stream().findFirst();
    }

    /**
     * @param studentId UUID
     * @return boolean
     * @throws SqlExecutionException Unexpected result
     */
    public boolean existsStudentById(UUID studentId) {
        String sql = """
                SELECT EXISTS (SELECT 1
                               FROM students s
                               WHERE s.id = :studentId
                               AND s.is_deleted = FALSE)
                    AS exists_by_id;
                """;
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("studentId", studentId);
        return jdbcTemplate.query(sql, param, CommonRowMapper.BOOLEAN).stream().findFirst()
                .orElseThrow(SqlExecutionException::unexpectedResult);
    }

    /**
     * @param studentDisplayId String
     * @param studentId        UUID
     * @return boolean
     * @throws SqlExecutionException Unexpected result
     */
    public boolean existsStudentByDisplayAndIdIsNot(String studentDisplayId, UUID studentId) {
        String sql = """
                SELECT EXISTS (SELECT 1
                               FROM students
                               WHERE display_id = :studentDisplayId
                               AND id != :studentId
                               AND is_deleted = FALSE)
                    AS exists_by_display_id;
                """;
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("studentDisplayId", studentDisplayId)
                .addValue("studentId", studentId);
        return jdbcTemplate.query(sql, param, CommonRowMapper.BOOLEAN).stream().findFirst()
                .orElseThrow(SqlExecutionException::unexpectedResult);
    }

    /**
     * @param request       CreateStudentRequest
     * @param currentUserId UUID
     * @throws SqlExecutionException Unexpected effect
     */
    public void updateStudent(UUID studentId, UpdateStudentRequest request, UUID currentUserId) {
        String sql = """
                UPDATE students
                SET display_id   = :displayId,
                    full_name    = :fullName,
                    phone_number = :phoneNumber,
                    status       = :status,
                    updated_at   = now(),
                    updated_by   = :currentUserId
                WHERE id = :studentId
                AND is_deleted = FALSE;
                """;
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("displayId", request.displayId())
                .addValue("fullName", request.fullName())
                .addValue("phoneNumber", request.phoneNumber())
                .addValue("status", request.status().getDescription())
                .addValue("currentUserId", currentUserId)
                .addValue("studentId", studentId);
        int affectedRows = jdbcTemplate.update(sql, param);
        if (affectedRows != 1) {
            throw SqlExecutionException.unexpectedEffect();
        }
    }

    /**
     * @param studentId     UUID
     * @param currentUserId UUID
     * @return Optional of soft deleted StudentDisplayTitle
     */
    public Optional<StudentDisplayTitle> softDeleteStudent(UUID studentId, UUID currentUserId) {
        String sql = """
                UPDATE students
                SET is_deleted = TRUE,
                    deleted_at = NOW(),
                    deleted_by = :currentUserId
                WHERE id = :studentId
                AND students.is_deleted = FALSE
                RETURNING display_id, full_name;
                """;
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("currentUserId", currentUserId)
                .addValue("studentId", studentId);
        return jdbcTemplate.query(sql, param, STUDENT_DISPLAY_TITLE_ROW_MAPPER).stream().findFirst();
    }
}
