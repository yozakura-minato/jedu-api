package com.jsal.jeduApi.student.util;

import com.jsal.jeduApi.common.exception.UtilityClassInstantiationException;

/**
 * Important: DO NOT instantiate or extend this class
 *
 * @author Yozakura Minato
 */
public final class StudentMessage {
    private StudentMessage() {
        throw new UtilityClassInstantiationException();
    }

    /**
     * @author Yozakura Minato
     */
    public static class Id {
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

    /**
     * @author Yozakura Minato
     */
    public static class Status {
        public static final String NULL = "student.status.null";
    }
}
