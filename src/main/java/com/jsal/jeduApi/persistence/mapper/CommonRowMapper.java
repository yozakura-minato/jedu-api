package com.jsal.jeduApi.persistence.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
public class CommonRowMapper {
    public static final RowMapper<Boolean> BOOLEAN
            = new SingleColumnRowMapper<>(Boolean.class);
    public static final RowMapper<UUID> UUID
            = new SingleColumnRowMapper<>(UUID.class);
    public static final RowMapper<LocalDateTime> LOCAL_DATE_TIME
            = new SingleColumnRowMapper<>(LocalDateTime.class);
}
