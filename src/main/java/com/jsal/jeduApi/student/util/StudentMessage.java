package com.jsal.jeduApi.student.util;

/**
 * @author Yozakura Minato
 */
public class StudentMessage {
    public static class DisplayId {
        public static final String NULL = "student.displayId.null";
        public static final String NOT_TRIMMED = "student.displayId.notTrimmed";

        public static final String NOT_FOUND = "student.displayId.notFound";
        public static final String CONFLICT = "student.displayId.conflict";
    }

    public static class FullName {
        public static final String NULL = "student.fullName.null";
        public static final String NOT_TRIMMED = "student.fullName.notTrimmed";
    }

    public static class Email {
        public static final String NULL = "student.email.null";
        public static final String INVALID_FORMAT = "student.email.invalidFormat";

        public static final String CONFLICT = "student.email.conflict";
    }

    public static class PhoneNumber {
        public static final String NOT_TRIMMED = "student.phoneNumber.notTrimmed";
    }

    public static class Password {
        public static final String NULL = "student.password.null";
        public static final String NOT_TRIMMED = "student.password.notTrimmed";
    }
}
