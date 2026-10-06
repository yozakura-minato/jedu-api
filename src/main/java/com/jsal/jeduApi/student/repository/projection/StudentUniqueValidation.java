package com.jsal.jeduApi.student.repository.projection;

/**
 * @param existsByDisplayId boolean
 * @param existsByEmail     boolean
 * @author Yozakura Minato
 */
public record StudentUniqueValidation(
        boolean existsByDisplayId,
        boolean existsByEmail
) {
}
