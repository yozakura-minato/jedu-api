package com.jsal.jeduApi.employee.service;

import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
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
    List<EmployeeTitle> findAllEmployeeTitles();

    /**
     * Find by {@code id}.
     * If exists then return, else then throw exception.
     *
     * @param id UUID
     * @return EmployeeProfile
     * @throws ResourceNotFoundException when {@code employeeId} is not found
     */
    EmployeeProfile findEmployeeProfileById(UUID id);
}
