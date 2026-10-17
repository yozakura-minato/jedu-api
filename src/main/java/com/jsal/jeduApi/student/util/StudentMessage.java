package com.jsal.jeduApi.student.util;

/**
 * @author Yozakura Minato
 */
public class StudentMessage {
    /**
     * @author Yozakura Minato
     */
    public static class Id {
        public static final String NULL = "student.id.null";

        public static final String NOT_FOUND = "student.id.notFound";
    }

    /**
     * @author Yozakura Minato
     */
    public static class DisplayId {
        public static final String NULL = "student.displayId.null";
        public static final String NOT_TRIMMED = "student.displayId.notTrimmed";

        public static final String CONFLICT = "student.displayId.conflict";
    }

    /**
     * @author Yozakura Minato
     */
    public static class FullName {
        public static final String NULL = "student.fullName.null";
        public static final String NOT_TRIMMED = "student.fullName.notTrimmed";
    }

    /**
     * @author Yozakura Minato
     */
    public static class Email {
        public static final String NULL = "student.email.null";
        public static final String INVALID_FORMAT = "student.email.invalidFormat";

        public static final String CONFLICT = "student.email.conflict";
    }

    /**
     * @author Yozakura Minato
     */
    public static class PhoneNumber {
        public static final String INVALID_FORMAT = "student.phoneNumber.invalidFormat";
    }

    /**
     * @author Yozakura Minato
     */
    public static class Password {
        public static final String NULL = "student.password.null";
        public static final String NOT_TRIMMED = "student.password.notTrimmed";
        public static final String WEAK = "student.password.weak";
    }
}
