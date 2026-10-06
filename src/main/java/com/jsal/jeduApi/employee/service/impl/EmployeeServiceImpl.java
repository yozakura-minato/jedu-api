package com.jsal.jeduApi.employee.service.impl;

import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
import com.jsal.jeduApi.employee.repository.EmployeeRepository;
import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;
import com.jsal.jeduApi.employee.service.EmployeeApiService;
import com.jsal.jeduApi.employee.util.EmployeeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Yozakura Minato
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeApiService {
    private final EmployeeRepository employeeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeProfile findProfileByDisplayId(String displayId) {
        return employeeRepository.selectProfileByDisplayId(displayId)
                .orElseThrow(() -> new ResourceNotFoundException(EmployeeMessage.DisplayId.NOT_FOUND));
    }
}
