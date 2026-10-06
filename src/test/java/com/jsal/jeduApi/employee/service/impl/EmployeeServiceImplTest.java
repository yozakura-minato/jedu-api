package com.jsal.jeduApi.employee.service.impl;

import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
import com.jsal.jeduApi.employee.repository.EmployeeRepository;
import com.jsal.jeduApi.employee.util.EmployeeMessage;
import com.jsal.jeduApi.employee.util.EmployeeMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
    private EmployeeServiceImpl employeeApiService;

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: findProfileByDisplayId")
    class FindProfileByDisplayIdTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD return employeeProfile WHEN displayId is valid")
        void findProfileByDisplayId_Success() {
            // Arrange
            var displayId = EmployeeMock.displayId();

            var expectedProfile = EmployeeMock.employeeProfile(null);
            when(employeeRepository.selectProfileByDisplayId(displayId)).thenReturn(Optional.of(expectedProfile));

            // Act
            var actualProfile = employeeApiService.findProfileByDisplayId(displayId);

            // Assert
            verify(employeeRepository, times(1)).selectProfileByDisplayId(displayId);

            assertEquals(expectedProfile, actualProfile);
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD throw exception WHEN displayId is not found")
        void findProfileByDisplayId_DisplayIdNotFound() {
            // Arrange
            var displayId = EmployeeMock.displayId();

            when(employeeRepository.selectProfileByDisplayId(displayId)).thenReturn(Optional.empty());

            // Act
            var exception = assertThrows(
                    ResourceNotFoundException.class, () -> employeeApiService.findProfileByDisplayId(displayId)
            );

            // Assert
            verify(employeeRepository, times(1)).selectProfileByDisplayId(displayId);

            assertEquals(EmployeeMessage.DisplayId.NOT_FOUND, exception.getMessage());
        }
    }
}