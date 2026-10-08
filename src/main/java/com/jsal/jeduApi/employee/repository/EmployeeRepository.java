package com.jsal.jeduApi.employee.repository;

import com.jsal.jeduApi.employee.repository.mapper.EmployeeParamMapper;
import com.jsal.jeduApi.employee.repository.mapper.EmployeeRowMapper;
import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;
import com.jsal.jeduApi.employee.repository.projection.EmployeeTitle;
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
public class EmployeeRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final EmployeeParamMapper employeeParamMapper;
    private final EmployeeRowMapper employeeRowMapper;

    /**
     * @return List of EmployeeTitle
     */
    public List<EmployeeTitle> selectAllTitles() {
        String selectSql = """
                select e.id, e.display_id, e.full_name
                from employees e
                where e.is_deleted = false;""";

        return jdbcTemplate.query(selectSql, employeeRowMapper.titleRowMapper());
    }

    /**
     * @param id UUID
     * @return Optional of EmployeeProfile
     */
    public Optional<EmployeeProfile> selectProfileById(UUID id) {
        String selectSql = """
                select
                    e.id,
                    e.display_id,
                    e.full_name,
                    e.email,
                    e.phone_number,
                    e.status,
                    d.name as "departments.name"
                from employees e
                    join employee_departments ed on e.id = ed.employee_id
                    join departments d on d.id = ed.department_id
                        and d.is_deleted = false
                where e.id = :id
                and e.is_deleted = false;""";
        MapSqlParameterSource selectParam = employeeParamMapper.idToSelectParam(id);

        return jdbcTemplate.query(selectSql, selectParam, employeeRowMapper.profileRowMapper()).stream().findFirst();
    }
}
