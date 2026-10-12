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

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: findAllEmployeeTitles")
    class FindAllTitlesTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD return title list WHEN called")
        void findAllTitles_success() {
            // Arrange
            var expectedTitleList = List.of(
                    new EmployeeTitle(UUID.randomUUID(), "ID01", "Tester 01"),
                    new EmployeeTitle(UUID.randomUUID(), "ID02", "Tester 02"),
                    new EmployeeTitle(UUID.randomUUID(), "ID03", "Tester 03")
            );
            when(employeeRepository.selectAllEmployeeTitles()).thenReturn(expectedTitleList);

            // Act
            var actualTitleList = employeeService.findAllEmployeeTitles();

            // Assert
            assertEquals(expectedTitleList, actualTitleList);

            verify(employeeRepository, times(1)).selectAllEmployeeTitles();
        }
    }

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: findEmployeeProfileById")
    class FindProfileByIdTest {
        private static final UUID ID = UUID.randomUUID();

        @Test
        @DisplayName(value = "Scenario 1: SHOULD return employeeProfile WHEN displayId is valid")
        void findProfileById_Success() {
            // Arrange
            var expectedProfile = new EmployeeProfile(
                    ID,
                    "id4tester",
                    "JEDU Tester",
                    "tester@jedu.dev",
                    null,
                    EmployeeStatus.ACTIVE,
                    List.of("Department 1", "Department 2", "Department 3")
            );
            when(employeeRepository.selectEmployeeProfileById(ID)).thenReturn(Optional.of(expectedProfile));

            // Act
            var actualProfile = employeeService.findEmployeeProfileById(ID);

            // Assert
            assertEquals(expectedProfile, actualProfile);

            verify(employeeRepository, times(1)).selectEmployeeProfileById(ID);
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD throw exception WHEN displayId is not found")
        void findProfileById_DisplayIdNotFound() {
            // Arrange
            when(employeeRepository.selectEmployeeProfileById(ID)).thenReturn(Optional.empty());

            // Act
            var exception = assertThrows(ResourceNotFoundException.class, () -> employeeService.findEmployeeProfileById(ID));

            // Assert
            assertEquals(EmployeeMessage.Id.NOT_FOUND, exception.getMessage());

            verify(employeeRepository, times(1)).selectEmployeeProfileById(ID);
        }
    }
}
