package com.jsal.jeduApi.employee.repository.mapper;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

/**
 * @author Yozakura Minato
 */
@Component
public class EmployeeParamsMapper {
    /**
     * @param displayId String
     * @return MapSqlParameterSource
     */
    public MapSqlParameterSource displayIdToSelectParam(String displayId) {
        return new MapSqlParameterSource().addValue("displayId", displayId);
    }
}
