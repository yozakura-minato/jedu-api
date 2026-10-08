package com.jsal.jeduApi.employee.repository.mapper;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Yozakura Minato
 */
@Component
public class EmployeeParamMapper {
    /**
     * @param id UUID
     * @return MapSqlParameterSource
     */
    public MapSqlParameterSource idToSelectParam(UUID id) {
        return new MapSqlParameterSource().addValue("id", id);
    }
}
