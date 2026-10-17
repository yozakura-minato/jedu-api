package com.jsal.jeduApi.employee.repository;

import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;
import com.jsal.jeduApi.employee.repository.projection.EmployeeTitle;
import lombok.RequiredArgsConstructor;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
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
public class EmployeeRepository {
    private static final RowMapper<EmployeeTitle> EMPLOYEE_TITLE_MAPPER = JdbcTemplateMapperFactory.newInstance()
            .addKeys("id").newRowMapper(EmployeeTitle.class);
    private static final ResultSetExtractor<List<EmployeeProfile>> EMPLOYEE_PROFILE_EXTRACTOR
            = JdbcTemplateMapperFactory.newInstance()
            .addKeys("id").newResultSetExtractor(EmployeeProfile.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * @return List of EmployeeTitle
     */
    public List<EmployeeTitle> selectAllEmployeeTitles() {
        String sql = """
                select e.id, e.display_id, e.full_name
                from employees e
                where e.is_deleted = false;""";
        return jdbcTemplate.query(sql, EMPLOYEE_TITLE_MAPPER);
    }

    /**
     * @param employeeId UUID
     * @return Optional of EmployeeProfile
     */
    public Optional<EmployeeProfile> selectEmployeeProfileById(UUID employeeId) {
        String sql = """
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
                where e.id = :employeeId
                and e.is_deleted = false;""";
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("employeeId", employeeId);
        return jdbcTemplate.query(sql, param, EMPLOYEE_PROFILE_EXTRACTOR).stream().findFirst();
    }
}
