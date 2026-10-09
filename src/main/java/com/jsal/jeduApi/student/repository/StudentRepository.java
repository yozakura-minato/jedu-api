package com.jsal.jeduApi.student.repository;

import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.repository.mapper.StudentParamsMapper;
import com.jsal.jeduApi.student.repository.mapper.StudentResultMapper;
import com.jsal.jeduApi.student.repository.projection.StudentProfile;
import com.jsal.jeduApi.student.repository.projection.StudentTitle;
import com.jsal.jeduApi.student.repository.projection.StudentUniqueValidation;
import lombok.RequiredArgsConstructor;
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
    private final StudentResultMapper studentResultMapper;

    /**
     * @param displayId String
     * @param email     Strong
     * @return Optional of StudentUniqueValidation
     */
    public Optional<StudentUniqueValidation> existsByDisplayIdOrEmail(String displayId, String email) {
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
        MapSqlParameterSource param = studentParamsMapper.fromDisplayIdAndEmail(displayId, email);
        return jdbcTemplate.query(query, param, studentResultMapper.toUniqueValidation()).stream().findFirst();
    }

    /**
     * @param createRequest CreateStudentRequest
     * @param currentUserId UUID
     * @return int: the number of rows affected
     */
    public int insert(CreateStudentRequest createRequest, UUID currentUserId) {
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
                    :createdBy
                );""";
        MapSqlParameterSource param = studentParamsMapper.fromCreateRequest(createRequest, currentUserId);
        return jdbcTemplate.update(query, param);
    }

    /**
     * @return List of StudentTitle
     */
    public List<StudentTitle> selectAllTitles() {
        String query = """
                select s.id, s.display_id, s.full_name
                from students s
                where s.is_deleted is false;""";

        return jdbcTemplate.query(query, studentResultMapper.toTitle());
    }

    /**
     * @param id UUID
     * @return Optional of StudentProfile
     */
    public Optional<StudentProfile> selectProfileById(UUID id) {
        String query = """
                select s.id, s.display_id, s.full_name, s.email, s.phone_number, s.status
                from students s
                where s.id = :id
                and s.is_deleted is false;""";
        MapSqlParameterSource param = studentParamsMapper.fromId(id);

        return jdbcTemplate.query(query, param, studentResultMapper.toProfile()).stream().findFirst();
    }
}
