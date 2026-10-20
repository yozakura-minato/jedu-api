package com.jsal.jeduApi.persistence.mapper;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * @author Yozakura Minato
 */
public class BaseMapperFactory {
    /**
     * @param targetClass {@code T}
     * @param <T>         The target class
     * @return Row mapper of {@code T}
     */
    public static <T> RowMapper<T> rowMapper(Class<T> targetClass) {
        return JdbcTemplateMapperFactory.newInstance().newRowMapper(targetClass);
    }

    /**
     * @param targetClass {@code T}
     * @param keys        String...
     * @param <T>         The target class
     * @return ResultSetExtractor of {@code T} list
     */
    public static <T> ResultSetExtractor<List<T>> resultSetExtractor(Class<T> targetClass, String... keys) {
        return JdbcTemplateMapperFactory.newInstance().addKeys(keys).newResultSetExtractor(targetClass);
    }
}
