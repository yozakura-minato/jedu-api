package com.jsal.jeduApi.student.repository.projection;

/**
 * @param displayId String
 * @param fullName  String
 * @author Yozakura Minato
 */
public record StudentDisplayTitle(
        String displayId,
        String fullName
) {
    /**
     * @return StudentDisplayTitle
     */
    public static StudentDisplayTitle mock() {
        return new StudentDisplayTitle("ST01", "Student One");
    }
}
