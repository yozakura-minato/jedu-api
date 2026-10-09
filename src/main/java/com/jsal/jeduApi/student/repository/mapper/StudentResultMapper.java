package com.jsal.jeduApi.student.repository.mapper;

import com.jsal.jeduApi.student.repository.projection.StudentProfile;
import com.jsal.jeduApi.student.repository.projection.StudentTitle;
import com.jsal.jeduApi.student.repository.projection.StudentUniqueValidation;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Yozakura Minato
 */
@Component
public class StudentResultMapper {
    /**
     * @return RowMapper of UUID
     */
    public RowMapper<UUID> toUuid() {
        return JdbcTemplateMapperFactory.newInstance()
                .addKeys("id").newRowMapper(UUID.class);
    }

    /**
     * @return RowMapper of StudentUniqueValidation
     */
    public RowMapper<StudentUniqueValidation> toUniqueValidation() {
        return JdbcTemplateMapperFactory.newInstance().newRowMapper(StudentUniqueValidation.class);
    }

    /**
     * @return RowMapper of StudentTitle
     */
    public RowMapper<StudentTitle> toTitle() {
        return JdbcTemplateMapperFactory.newInstance()
                .addKeys("id").newRowMapper(StudentTitle.class);
    }

    /**
     * @return RowMapper of StudentProfile
     */
    public RowMapper<StudentProfile> toProfile() {
        return JdbcTemplateMapperFactory.newInstance()
                .addKeys("id").newRowMapper(StudentProfile.class);
    }
}
