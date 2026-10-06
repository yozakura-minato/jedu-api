package com.jsal.jeduApi.employee.util;

import com.jsal.jeduApi.employee.model.EmployeeStatus;
import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;

/**
 * @author Yozakura Minato
 */
public class EmployeeMock {
    public static String displayId() {
        return "ID4TEST";
    }

    public static EmployeeProfile employeeProfile(String phoneNumber) {
        return new EmployeeProfile(
                "ID4TEST",
                "Tester",
                "tester@jedu.com.vn",
                phoneNumber,
                EmployeeStatus.ACTIVE
        );
    }
}
