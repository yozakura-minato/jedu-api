package com.jsal.jeduApi.employee.service.impl;

import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
import com.jsal.jeduApi.employee.repository.EmployeeRepository;
import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;
import com.jsal.jeduApi.employee.util.EmployeeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author Yozakura Minato
 */
@ExtendWith(value = MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private static final UUID EMPLOYEE_ID = UUID.randomUUID();

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: EmployeeApiService.findAllEmployes")
    class FindAllEmployeesTest {
        @Test
        @DisplayName(value = "Scenario 1: Success")
        void findAllEmployeesTest_Success() {
            // Arrange
            var expectedEmployeeList = EmployeeProfile.mockList();
            when(employeeRepository.selectAllEmployees()).thenReturn(expectedEmployeeList);

            // Act
            var actualEmployeeList = employeeService.findAllEmployees();

            // Assert
            assertEquals(expectedEmployeeList, actualEmployeeList);

            verify(employeeRepository, times(1)).selectAllEmployees();
        }
    }

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: EmployeeApiService.findEmployeeById")
    class FindEmployeeByIdTest {
        @Test
        @DisplayName(value = "Scenario 1: Success")
        void findEmployeeById_Success() {
            // Arrange
            var expectedEmployee = EmployeeProfile.mockWithId(EMPLOYEE_ID);
            when(employeeRepository.selectEmployeeById(EMPLOYEE_ID))
                    .thenReturn(Optional.of(expectedEmployee));

            // Act
            var actualEmployee = employeeService.findEmployeeById(EMPLOYEE_ID);

            // Assert
            assertEquals(expectedEmployee, actualEmployee);

            verify(employeeRepository, times(1)).selectEmployeeById(EMPLOYEE_ID);
        }

        @Test
        @DisplayName(value = "Scenario 2: Employee ID not found")
        void findEmployeeById_IdNotFound() {
            // Arrange
            when(employeeRepository.selectEmployeeById(EMPLOYEE_ID)).thenReturn(Optional.empty());

            // Act
            var exception = assertThrows(ResourceNotFoundException.class,
                    () -> employeeService.findEmployeeById(EMPLOYEE_ID));

            // Assert
            assertEquals(EmployeeMessage.Id.NOT_FOUND, exception.getMessage());

            verify(employeeRepository, times(1)).selectEmployeeById(EMPLOYEE_ID);
        }
    }
}
