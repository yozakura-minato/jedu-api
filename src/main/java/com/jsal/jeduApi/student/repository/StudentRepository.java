package com.jsal.jeduApi.student.repository;

import com.jsal.jeduApi.common.exception.SqlExecutionException;
import com.jsal.jeduApi.persistence.util.PersistenceMapper;
import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.controller.request.UpdateStudentRequest;
import com.jsal.jeduApi.student.repository.projection.StudentProfile;
import com.jsal.jeduApi.student.repository.projection.StudentTitle;
import com.jsal.jeduApi.student.repository.projection.StudentUniqueValidation;
import com.jsal.jeduApi.student.repository.type.StudentStatus;
import lombok.RequiredArgsConstructor;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
@Repository
@RequiredArgsConstructor
public class StudentRepository {
    private static final RowMapper<StudentUniqueValidation> STUDENT_UNIQUE_VALIDATION_MAPPER
            = JdbcTemplateMapperFactory.newInstance().newRowMapper(StudentUniqueValidation.class);
    private static final RowMapper<StudentTitle> STUDENT_TITLE_MAPPER = JdbcTemplateMapperFactory.newInstance()
            .addKeys("id").newRowMapper(StudentTitle.class);
    private static final RowMapper<StudentProfile> STUDENT_PROFILE_MAPPER = JdbcTemplateMapperFactory.newInstance()
            .addKeys("id").newRowMapper(StudentProfile.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * @param studentDisplayId String
     * @param studentEmail     Strong
     * @return StudentUniqueValidation
     * @throws SqlExecutionException when SQL execution returns nothing
     */
    public StudentUniqueValidation existsStudentByDisplayIdOrEmail(String studentDisplayId, String studentEmail) {
        String sql = """
                select
                    exists (
                        select 1
                        from students s
                        where s.display_id = :studentDisplayId
                        and s.is_deleted = false
                    ) as exists_by_display_id,
                    exists (
                        select 1
                        from students s
                        where s.email = :studentEmail
                        and s.is_deleted = false
                    ) as exists_by_email;""";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("studentDisplayId", studentDisplayId).addValue("studentEmail", studentEmail);
        return jdbcTemplate.query(sql, param, STUDENT_UNIQUE_VALIDATION_MAPPER).stream().findFirst()
                .orElseThrow(SqlExecutionException::unexpectedResult);
    }

    /**
     * @param request       CreateStudentRequest
     * @param studentStatus StudentStatus
     * @param currentUserId UUID
     * @return UUID: of created student
     * @throws SqlExecutionException when SQL execution returns nothing
     */
    public UUID insertStudent(CreateStudentRequest request, StudentStatus studentStatus, UUID currentUserId) {
        String sql = """
                insert into students (
                    display_id,
                    full_name,
                    email,
                    phone_number,
                    hashed_password,
                    status,
                    created_at,
                    created_by
                ) values (
                    :displayId,
                    :fullName,
                    :email,
                    :phoneNumber,
                    :hashedPassword,
                    :studentStatus,
                    now(),
                    :currentUserId
                ) returning id;""";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("displayId", request.displayId())
                .addValue("fullName", request.fullName())
                .addValue("email", request.email())
                .addValue("phoneNumber", request.phoneNumber())
                .addValue("hashedPassword", request.password())
                .addValue("studentStatus", studentStatus.getDescription())
                .addValue("currentUserId", currentUserId);
        return jdbcTemplate.query(sql, param, PersistenceMapper.uuidMapper()).stream().findFirst()
                .orElseThrow(SqlExecutionException::unexpectedResult);
    }

    /**
     * @return List of StudentTitle
     */
    public List<StudentTitle> selectAllStudentTitles() {
        String sql = """
                select s.id, s.display_id, s.full_name
                from students s
                where s.is_deleted is false;""";
        return jdbcTemplate.query(sql, STUDENT_TITLE_MAPPER);
    }

    /**
     * @param studentId UUID
     * @return Optional of StudentProfile
     */
    public Optional<StudentProfile> selectStudentProfileById(UUID studentId) {
        String sql = """
                select s.id, s.display_id, s.full_name, s.email, s.phone_number, s.status
                from students s
                where s.id = :studentId
                and s.is_deleted is false;""";
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("studentId", studentId);
        return jdbcTemplate.query(sql, param, STUDENT_PROFILE_MAPPER).stream().findFirst();
    }

    /**
     * @param studentId UUID
     * @return boolean
     * @throws SqlExecutionException when SQL execution returns nothing
     */
    public boolean existsStudentById(UUID studentId) {
        String sql = """
                select exists (
                    select 1
                    from students s
                    where s.id = :studentId
                    and s.is_deleted = false
                ) as exists_by_id;""";
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("studentId", studentId);
        return jdbcTemplate.query(sql, param, PersistenceMapper.booleanMapper()).stream().findFirst()
                .orElseThrow(SqlExecutionException::unexpectedResult);
    }

    /**
     * @param studentDisplayId String
     * @param studentId        UUID
     * @return boolean
     * @throws SqlExecutionException when SQL execution is nothing
     */
    public boolean existsStudentByDisplayAndIdIsNot(String studentDisplayId, UUID studentId) {
        String sql = """
                select exists (
                    select 1
                    from students s
                    where s.display_id = :studentDisplayId
                    and s.id != :studentId
                    and s.is_deleted = false
                ) as exists_by_display_id;""";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("studentDisplayId", studentDisplayId).addValue("studentId", studentId);
        return jdbcTemplate.query(sql, param, PersistenceMapper.booleanMapper()).stream().findFirst()
                .orElseThrow(SqlExecutionException::unexpectedResult);
    }

    /**
     * @param request       CreateStudentRequest
     * @param currentUserId UUID
     * @return LocalDateTime: student updated time
     * @throws SqlExecutionException when SQL execution returns nothing
     */
    public LocalDateTime updateStudent(UUID studentId, UpdateStudentRequest request, UUID currentUserId) {
        String sql = """
                update students s
                set
                    display_id = :displayId,
                    full_name = :fullName,
                    phone_number = :phoneNumber,
                    status = :status,
                    updated_at = now(),
                    updated_by = :currentUserId
                where s.id = :studentId
                and s.is_deleted = false
                returning s.updated_at;""";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("displayId", request.displayId())
                .addValue("fullName", request.fullName())
                .addValue("phoneNumber", request.phoneNumber())
                .addValue("status", request.status().getDescription())
                .addValue("currentUserId", currentUserId)
                .addValue("studentId", studentId);
        return jdbcTemplate.query(sql, param, PersistenceMapper.localDateTimeMapper()).stream().findFirst()
                .orElseThrow(SqlExecutionException::unexpectedResult);
    }

    /**
     * @param studentId     UUID
     * @param currentUserId UUID
     * @return Student soft deleted time
     * @throws SqlExecutionException when SQL execution returns nothing
     */
    public LocalDateTime softDeleteStudent(UUID studentId, UUID currentUserId) {
        String sql = """
                update students s
                set
                    is_deleted = true,
                    deleted_at = now(),
                    deleted_by = :currentUserId
                where s.id = :studentId
                returning s.deleted_at;""";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("currentUserId", currentUserId).addValue("studentId", studentId);
        return jdbcTemplate.query(sql, param, PersistenceMapper.localDateTimeMapper()).stream().findFirst()
                .orElseThrow(SqlExecutionException::unexpectedResult);
    }
}
