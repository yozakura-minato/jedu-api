package com.jsal.jeduApi.persistence.util;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
public class PersistenceMapper {
    public static final RowMapper<Boolean> BOOLEAN_MAPPER = new SingleColumnRowMapper<>(Boolean.class);
    public static final RowMapper<UUID> UUID_MAPPER = new SingleColumnRowMapper<>(UUID.class);
    public static final RowMapper<LocalDateTime> LOCAL_DATE_TIME_MAPPER
            = new SingleColumnRowMapper<>(LocalDateTime.class);
}
