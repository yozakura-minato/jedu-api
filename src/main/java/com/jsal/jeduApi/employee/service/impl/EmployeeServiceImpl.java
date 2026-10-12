package com.jsal.jeduApi.employee.service.impl;

import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
import com.jsal.jeduApi.employee.repository.EmployeeRepository;
import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;
import com.jsal.jeduApi.employee.repository.projection.EmployeeTitle;
import com.jsal.jeduApi.employee.service.EmployeeApiService;
import com.jsal.jeduApi.employee.util.EmployeeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeApiService {
    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeTitle> findAllEmployeeTitles() {
        return employeeRepository.selectAllEmployeeTitles();
    }

    /**
     * {@inheritDoc}
     */
    public EmployeeProfile findEmployeeProfileById(UUID id) {
        return employeeRepository.selectEmployeeProfileById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EmployeeMessage.Id.NOT_FOUND));
    }
}
