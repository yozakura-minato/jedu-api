package com.jsal.jeduApi.security.util;

import com.jsal.jeduApi.common.exception.UtilityClassInstantiationException;

import java.util.UUID;

/**
 * Important: DO NOT instantiate or extend this class
 *
 * @author Yozakura Minato
 */
public final class SecurityConstant {
    private SecurityConstant() {
        throw new UtilityClassInstantiationException();
    }

    /**
     * @deprecated This constant will be removed after MVP
     */
    @Deprecated
    public static final UUID CURRENT_EMPLOYEE_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
}
