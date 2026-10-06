package com.jsal.jeduApi.common.util;

import java.util.UUID;

/**
 * @author Yozakura Minato
 */
public class CommonConstant {
    public static final UUID SYSTEM_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    /**
     * @deprecated This constant will be removed after MVP
     */
    @Deprecated
    public static final UUID CURRENT_EMPLOYEE_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
}
