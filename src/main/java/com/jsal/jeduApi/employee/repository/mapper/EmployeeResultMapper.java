package com.jsal.jeduApi.employee.repository.mapper;

import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;
import com.jsal.jeduApi.employee.repository.projection.EmployeeTitle;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Yozakura Minato
 */
@Component
public class EmployeeResultMapper {
    /**
     * @return RowMapper of EmployeeTitle
     */
    public RowMapper<EmployeeTitle> toTitle() {
        return JdbcTemplateMapperFactory.newInstance()
                .addKeys("id").newRowMapper(EmployeeTitle.class);
    }

    /**
     * @return ResultSetExtractor of EmployeeProfile List
     */
    public ResultSetExtractor<List<EmployeeProfile>> toProfile() {
        return JdbcTemplateMapperFactory.newInstance()
                .addKeys("id").newResultSetExtractor(EmployeeProfile.class);
    }
}
