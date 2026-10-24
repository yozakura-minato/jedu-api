package com.jsal.jeduApi.persistence.util;

import com.jsal.jeduApi.common.exception.UtilityClassInstantiationException;

import java.util.UUID;

/**
 * Important: DO NOT instantiate or extend this class
 *
 * @author Yozakura Minato
 */
public final class PersistenceConstant {
    private PersistenceConstant() {
        throw new UtilityClassInstantiationException();
    }

    public static final UUID SYSTEM_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
}
