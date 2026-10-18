package com.jsal.jeduApi.employee.service.impl;

import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
import com.jsal.jeduApi.employee.repository.EmployeeRepository;
import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;
import com.jsal.jeduApi.employee.repository.projection.EmployeeTitle;
import com.jsal.jeduApi.employee.repository.type.EmployeeStatus;
import com.jsal.jeduApi.employee.util.EmployeeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
    @DisplayName(value = "Test for: EmployeeApiService.findAllEmployeeTitles")
    class FindAllEmployeeTitlesTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD return employee title list WHEN success")
        void findAllEmployeeTitles_Success() {
            // Arrange
            var expectedEmployeeTitleList = List.of(
                    new EmployeeTitle(UUID.randomUUID(), "ID01", "Tester 01"),
                    new EmployeeTitle(UUID.randomUUID(), "ID02", "Tester 02"),
                    new EmployeeTitle(UUID.randomUUID(), "ID03", "Tester 03")
            );
            when(employeeRepository.selectAllEmployeeTitles()).thenReturn(expectedEmployeeTitleList);

            // Act
            var actualEmployeeTitleList = employeeService.findAllEmployeeTitles();

            // Assert
            assertEquals(expectedEmployeeTitleList, actualEmployeeTitleList);

            verify(employeeRepository, times(1)).selectAllEmployeeTitles();
        }
    }

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: EmployeeApiService.findEmployeeProfileById")
    class FindEmployeeProfileByIdTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD return employee profile WHEN success")
        void findEmployeeProfileById_Success() {
            // Arrange
            var expectedEmployeeProfile = new EmployeeProfile(
                    EMPLOYEE_ID,
                    "id4tester",
                    "JEDU Tester",
                    "tester@jedu.dev",
                    null,
                    EmployeeStatus.ACTIVE,
                    List.of("Department 1", "Department 2", "Department 3")
            );
            when(employeeRepository.selectEmployeeProfileById(EMPLOYEE_ID))
                    .thenReturn(Optional.of(expectedEmployeeProfile));

            // Act
            var actualEmployeeProfile = employeeService.findEmployeeProfileById(EMPLOYEE_ID);

            // Assert
            assertEquals(expectedEmployeeProfile, actualEmployeeProfile);

            verify(employeeRepository, times(1)).selectEmployeeProfileById(EMPLOYEE_ID);
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD throw exception WHEN ID not found")
        void findEmployeeProfileById_IdNotFound() {
            // Arrange
            when(employeeRepository.selectEmployeeProfileById(EMPLOYEE_ID)).thenReturn(Optional.empty());

            // Act
            var exception = assertThrows(ResourceNotFoundException.class,
                    () -> employeeService.findEmployeeProfileById(EMPLOYEE_ID));

            // Assert
            assertEquals(EmployeeMessage.Id.NOT_FOUND, exception.getMessage());

            verify(employeeRepository, times(1)).selectEmployeeProfileById(EMPLOYEE_ID);
        }
    }
}
