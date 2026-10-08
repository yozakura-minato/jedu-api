package com.jsal.jeduApi.employee.service;

import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;
import com.jsal.jeduApi.employee.repository.projection.EmployeeTitle;

import java.util.List;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
public interface EmployeeApiService {
    /**
     * @return List of EmployeeTitle
     */
    List<EmployeeTitle> findAllTitles();

    /**
     * @param id UUID
     * @return EmployeeProfile
     * @throws com.jsal.jeduApi.common.exception.ResourceNotFoundException when {@code employeeId} is not found
     */
    EmployeeProfile findProfileById(UUID id);
}
