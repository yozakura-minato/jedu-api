package com.jsal.jeduApi.employee.util;

import com.jsal.jeduApi.common.exception.UtilityClassInstantiationException;

/**
 * Important: DO NOT instantiate or extend this class
 *
 * @author Yozakura Minato
 */
public final class EmployeeMessage {
    private EmployeeMessage() {
        throw new UtilityClassInstantiationException();
    }

    /**
     * @author Yozakura Minato
     */
    public static class Id {
        public static final String NOT_FOUND = "employee.id.notFound";
    }
}
