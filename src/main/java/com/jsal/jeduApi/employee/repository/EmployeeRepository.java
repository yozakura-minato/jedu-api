package com.jsal.jeduApi.employee.repository;

import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;
import com.jsal.jeduApi.employee.repository.projection.EmployeeTitle;
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
public class EmployeeRepository {
    private static final RowMapper<EmployeeTitle> EMPLOYEE_TITLE_MAPPER
            = new DataClassRowMapper<>(EmployeeTitle.class);
    private static final RowMapper<EmployeeProfile> EMPLOYEE_PROFILE_MAPPER
            = new DataClassRowMapper<>(EmployeeProfile.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EmployeeRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return List of EmployeeTitle
     */
    public List<EmployeeTitle> selectAllEmployeeTitles() {
        String sql = """
                SELECT id, display_id, full_name
                FROM employees
                WHERE is_deleted = false;
                """;
        return jdbcTemplate.query(sql, EMPLOYEE_TITLE_MAPPER);
    }

    /**
     * @param employeeId UUID
     * @return Optional of EmployeeProfile
     */
    public Optional<EmployeeProfile> selectEmployeeProfileById(UUID employeeId) {
        String sql = """
                SELECT empl.id,
                       empl.display_id,
                       empl.full_name,
                       empl.email,
                       empl.phone_number,
                       empl.status,
                       dept.name AS department
                FROM employees empl
                    JOIN departments dept ON empl.department_id = dept.id
                    AND dept.is_deleted = FALSE
                WHERE empl.id = :employeeId
                AND empl.is_deleted = FALSE;
                """;
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("employeeId", employeeId);
        return jdbcTemplate.query(sql, param, EMPLOYEE_PROFILE_MAPPER).stream().findFirst();
    }
}
