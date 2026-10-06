package com.jsal.jeduApi.student.repository;

import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.repository.mapper.StudentParamsMapper;
import com.jsal.jeduApi.student.repository.projection.StudentProfile;
import com.jsal.jeduApi.student.repository.projection.StudentUniqueValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
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
    private final StudentParamsMapper studentParamsMapper;

    private static final DataClassRowMapper<StudentProfile> studentProfileRowMapper
            = DataClassRowMapper.newInstance(StudentProfile.class);
    private static final DataClassRowMapper<StudentUniqueValidation> studentUniqueValidationMapper
            = DataClassRowMapper.newInstance(StudentUniqueValidation.class);

    /**
     * @param insertParams Map of String keys and any values
     * @return true if affectedRows == 1, or else false
     */
    public boolean insert(CreateStudentRequest createRequest, UUID currentUserId) {
        String insertSql = """
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
                    :createdBy
                );""";
        MapSqlParameterSource insertParams = studentParamsMapper.createRequestToInsertParams(
                createRequest, currentUserId
        );

        int affectedRows = jdbcTemplate.update(insertSql, insertParams);
        return affectedRows == 1;
    }

    /**
     * @param displayId String
     * @param email     Strong
     * @return Optional of StudentUniqueValidation
     */
    public Optional<StudentUniqueValidation> existsByDisplayIdOrEmail(String displayId, String email) {
        String selectSql = """
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
        MapSqlParameterSource selectParms = studentParamsMapper.displayIdAndEmailToSelectParams(displayId, email);

        return jdbcTemplate.query(selectSql, selectParms, studentUniqueValidationMapper).stream().findFirst();
    }

    /**
     * @return List of StudentProfile
     */
    public List<StudentProfile> selectAllProfiles() {
        String selectSql = """
                select s.display_id, s.full_name, s.email, s.phone_number, s.status
                from students s
                where s.is_deleted is false;""";

        return jdbcTemplate.query(selectSql, studentProfileRowMapper);
    }

    /**
     * @param displayId String
     * @return Optional of StudentProfile
     */
    public Optional<StudentProfile> selectProfileByDisplayId(String displayId) {
        String selectSql = """
                select s.display_id, s.full_name, s.email, s.phone_number, s.status
                from students s
                where s.display_id = :displayId
                and s.is_deleted is false;""";
        MapSqlParameterSource selectParam = studentParamsMapper.displayIdToSelectParam(displayId);

        return jdbcTemplate.query(selectSql, selectParam, studentProfileRowMapper).stream().findFirst();
    }
}
