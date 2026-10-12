package com.jsal.jeduApi.student.repository;

import com.jsal.jeduApi.common.exception.SqlExecutionException;
import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
@Repository
@RequiredArgsConstructor
public class StudentRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * @param displayId String
     * @param email     Strong
     * @return StudentUniqueValidation
     * @throws SqlExecutionException when SQL result is null
     */
    public StudentUniqueValidation existsStudentByDisplayIdOrEmail(String displayId, String email) {
        String query = """
                select
                    exists (
                        select 1
                        from students s
                        where s.display_id = :displayId
                        and s.is_deleted = false
                    ) as exists_by_display_id,
                    exists (
                        select 1
                        from students s
                        where s.email = :email
                        and s.is_deleted = false
                    ) as exists_by_email;""";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("displayId", displayId)
                .addValue("email", email);
        RowMapper<StudentUniqueValidation> mapper = JdbcTemplateMapperFactory.newInstance()
                .newRowMapper(StudentUniqueValidation.class);

        return jdbcTemplate.query(query, param, mapper).stream().findFirst()
                .orElseThrow(SqlExecutionException::unexpectedResult);
    }

    /**
     * @param createRequest CreateStudentRequest
     * @param status        StudentStatus
     * @param currentUserId UUID
     * @return UUID: of created student
     * @throws SqlExecutionException when the number of effected rows is not 1
     */
    public UUID insertStudent(CreateStudentRequest createRequest, StudentStatus status, UUID currentUserId) {
        String query = """
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
                    :status,
                    now(),
                    :currentUserId
                ) returning id;""";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("displayId", createRequest.displayId())
                .addValue("fullName", createRequest.fullName())
                .addValue("email", createRequest.email())
                .addValue("phoneNumber", createRequest.phoneNumber())
                .addValue("hashedPassword", createRequest.password())
                .addValue("status", status.getDescription())
                .addValue("currentUserId", currentUserId);
        RowMapper<UUID> mapper = JdbcTemplateMapperFactory.newInstance()
                .addKeys("id").newRowMapper(UUID.class);

        return jdbcTemplate.query(query, param, mapper).stream().findFirst()
                .orElseThrow(SqlExecutionException::unexpectedResult);
    }

    /**
     * @return List of StudentTitle
     */
    public List<StudentTitle> selectAllStudentTitles() {
        String query = """
                select s.id, s.display_id, s.full_name
                from students s
                where s.is_deleted is false;""";
        RowMapper<StudentTitle> mapper = JdbcTemplateMapperFactory.newInstance()
                .addKeys("id").newRowMapper(StudentTitle.class);

        return jdbcTemplate.query(query, mapper);
    }

    /**
     * @param id UUID
     * @return Optional of StudentProfile
     */
    public Optional<StudentProfile> selectStudentProfileById(UUID id) {
        String query = """
                select s.id, s.display_id, s.full_name, s.email, s.phone_number, s.status
                from students s
                where s.id = :id
                and s.is_deleted is false;""";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);
        RowMapper<StudentProfile> mapper = JdbcTemplateMapperFactory.newInstance()
                .addKeys("id").newRowMapper(StudentProfile.class);

        return jdbcTemplate.query(query, param, mapper).stream().findFirst();
    }
}
