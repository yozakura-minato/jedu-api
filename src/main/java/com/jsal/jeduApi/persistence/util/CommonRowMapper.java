package com.jsal.jeduApi.persistence.util;

import com.jsal.jeduApi.common.exception.UtilityClassInstantiationException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Important: DO NOT instantiate or extend this class
 *
 * @author Yozakura Minato
 */
public final class CommonRowMapper {
    private CommonRowMapper() {
        throw new UtilityClassInstantiationException();
    }

    public static final RowMapper<Boolean> BOOLEAN
            = new SingleColumnRowMapper<>(Boolean.class);
    public static final RowMapper<UUID> UUID
            = new SingleColumnRowMapper<>(UUID.class);
    public static final RowMapper<LocalDateTime> LOCAL_DATE_TIME
            = new SingleColumnRowMapper<>(LocalDateTime.class);
}
