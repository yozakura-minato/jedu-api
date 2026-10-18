package com.jsal.jeduApi.student.repository.projection;


import java.util.UUID;

/**
 *
 * @param id        UUID
 * @param displayId String
 * @param fullName  String
 * @author Yozakura Minato
 */
public record StudentTitle(
        UUID id,
        String displayId,
        String fullName
) {
}
