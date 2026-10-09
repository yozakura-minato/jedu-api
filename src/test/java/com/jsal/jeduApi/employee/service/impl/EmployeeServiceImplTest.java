package com.jsal.jeduApi.employee.service.impl;

import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
import com.jsal.jeduApi.employee.repository.EmployeeRepository;
import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;
import com.jsal.jeduApi.employee.repository.projection.EmployeeTitle;
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

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: findAllTitles")
    class FindAllTitlesTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD return title list WHEN called")
        void findAllTitles_success() {
            // Arrange
            var expectedTitleList = EmployeeTitle.mockList();
            when(employeeRepository.selectAllTitles()).thenReturn(expectedTitleList);

            // Act
            var actualTitleList = employeeService.findAllTitles();

            // Assert
            assertEquals(expectedTitleList, actualTitleList);

            verify(employeeRepository, times(1)).selectAllTitles();
        }
    }

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: findProfileById")
    class FindProfileByIdTest {
        private static final UUID ID = UUID.randomUUID();

        @Test
        @DisplayName(value = "Scenario 1: SHOULD return employeeProfile WHEN displayId is valid")
        void findProfileById_Success() {
            // Arrange
            var expectedProfile = EmployeeProfile.mockWithId(ID);
            when(employeeRepository.selectProfileById(ID)).thenReturn(Optional.of(expectedProfile));

            // Act
            var actualProfile = employeeService.findProfileById(ID);

            // Assert
            assertEquals(expectedProfile, actualProfile);

            verify(employeeRepository, times(1)).selectProfileById(ID);
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD throw exception WHEN displayId is not found")
        void findProfileById_DisplayIdNotFound() {
            // Arrange
            when(employeeRepository.selectProfileById(ID)).thenReturn(Optional.empty());

            // Act
            var exception = assertThrows(ResourceNotFoundException.class, () -> employeeService.findProfileById(ID));

            // Assert
            assertEquals(EmployeeMessage.Id.NOT_FOUND, exception.getMessage());

            verify(employeeRepository, times(1)).selectProfileById(ID);
        }
    }
}
