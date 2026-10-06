package com.jsal.jeduApi.employee.service;

import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;

/**
 * @author Yozakura Minato
 */
public interface EmployeeApiService {
    /**
     * @param displayId String
     * @return EmployeeProfile
     * @throws com.jsal.jeduApi.common.exception.ResourceNotFoundException when {@code employeeId} is not found
     */
    EmployeeProfile findProfileByDisplayId(String displayId);
}
