package com.jsal.jeduApi.employee.repository;

import com.jsal.jeduApi.employee.repository.mapper.EmployeeParamsMapper;
import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Yozakura Minato
 */
@Repository
@RequiredArgsConstructor
public class EmployeeRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final EmployeeParamsMapper employeeParamsMapper;

    private static final DataClassRowMapper<EmployeeProfile> employeeProfileMapper
            = DataClassRowMapper.newInstance(EmployeeProfile.class);

    /**
     * @param displayId String
     * @return Optional of EmployeeProfile
     */
    public Optional<EmployeeProfile> selectProfileByDisplayId(String displayId) {
        String selectSql = """
                select e.display_id, e.full_name, e.email, e.phone_number, e.status
                from employees e
                where e.display_id = :displayId
                and e.is_deleted = false;""";
        MapSqlParameterSource selectParam = employeeParamsMapper.displayIdToSelectParam(displayId);

        return jdbcTemplate.query(selectSql, selectParam, employeeProfileMapper)
                .stream().findFirst();
    }
}
