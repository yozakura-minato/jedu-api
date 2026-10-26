package com.jsal.jeduApi.employee.service;

import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;

import java.util.List;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
public interface EmployeeApiService {
    /**
     * @return List of EmployeeProfile
     */
    List<EmployeeProfile> findAllEmployeeProfiles();

    /**
     * <ol>
     *     <li>If {@code employeeId} not exist, then throw exception</li>
     *     <li>Return</li>
     * </ol>
     *
     * @param employeeId UUID
     * @return EmployeeProfile
     * @throws ResourceNotFoundException when {@code employeeId} is not found
     */
    EmployeeProfile findEmployeeProfileById(UUID employeeId);
}
