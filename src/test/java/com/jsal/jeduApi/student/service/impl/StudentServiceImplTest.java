package com.jsal.jeduApi.student.service.impl;

import com.jsal.jeduApi.common.exception.ResourceConflictException;
import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.repository.StudentRepository;
import com.jsal.jeduApi.student.repository.projection.StudentProfile;
import com.jsal.jeduApi.student.repository.projection.StudentTitle;
import com.jsal.jeduApi.student.repository.projection.StudentUniqueValidation;
import com.jsal.jeduApi.student.repository.type.StudentStatus;
import com.jsal.jeduApi.student.util.StudentMessage;
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
class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: create")
    class CreateTest {
        private static final CreateStudentRequest CREATE_REQUEST = CreateStudentRequest.mock();
        private static final UUID CURRENT_USER_ID = UUID.randomUUID();

        private static final String DISPLAY_ID = CREATE_REQUEST.displayId();
        private static final String EMAIL = CREATE_REQUEST.email();

        @Test
        @DisplayName(value = "Scenario 1: SHOULD success WHEN request is valid")
        void create_Success() {
            // Arrange
            var uniqueValidation = new StudentUniqueValidation(false, false);
            when(studentRepository.existsByDisplayIdOrEmail(DISPLAY_ID, EMAIL))
                    .thenReturn(uniqueValidation);

            // Act
            studentService.create(CREATE_REQUEST, CURRENT_USER_ID);

            // Assert
            verify(studentRepository, times(1))
                    .insert(CREATE_REQUEST, StudentStatus.CREATED, CURRENT_USER_ID);

            verify(studentRepository, times(1)).existsByDisplayIdOrEmail(DISPLAY_ID, EMAIL);
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD throw exception WHEN displayId conflicts")
        void create_DisplayIdConflict() {
            // Arrange
            var uniqueValidation = new StudentUniqueValidation(true, false);
            when(studentRepository.existsByDisplayIdOrEmail(DISPLAY_ID, EMAIL)).thenReturn(uniqueValidation);

            // Act
            var exception = assertThrows(ResourceConflictException.class,
                    () -> studentService.create(CREATE_REQUEST, CURRENT_USER_ID));

            // Assert
            assertEquals(StudentMessage.DisplayId.CONFLICT, exception.getMessage());

            verify(studentRepository, times(1)).existsByDisplayIdOrEmail(DISPLAY_ID, EMAIL);
            verify(studentRepository, never()).insert(any(), any(), any());
        }

        @Test
        @DisplayName(value = "Scenario 3: SHOULD throw exception WHEN email conflicts")
        void create_EmailConflict() {
            // Arrange
            var uniqueValidation = new StudentUniqueValidation(false, true);
            when(studentRepository.existsByDisplayIdOrEmail(DISPLAY_ID, EMAIL)).thenReturn(uniqueValidation);

            // Act
            var exception = assertThrows(ResourceConflictException.class,
                    () -> studentService.create(CREATE_REQUEST, CURRENT_USER_ID));

            // Assert
            assertEquals(StudentMessage.Email.CONFLICT, exception.getMessage());

            verify(studentRepository, times(1)).existsByDisplayIdOrEmail(DISPLAY_ID, EMAIL);
            verify(studentRepository, never()).insert(any(), any(), any());
        }

        @Test
        @DisplayName(value = "Scenario 4: SHOULD throw exception WHEN both displayId and email conflict")
        void create_DisplayIdAndEmailConflict() {
            // Arrange
            var uniqueValidation = new StudentUniqueValidation(true, true);
            when(studentRepository.existsByDisplayIdOrEmail(DISPLAY_ID, EMAIL)).thenReturn(uniqueValidation);

            // Act
            var exception = assertThrows(ResourceConflictException.class,
                    () -> studentService.create(CREATE_REQUEST, CURRENT_USER_ID));

            // Assert
            assertEquals(StudentMessage.DisplayId.CONFLICT, exception.getMessage());

            verify(studentRepository, times(1)).existsByDisplayIdOrEmail(DISPLAY_ID, EMAIL);
            verify(studentRepository, never()).insert(any(), any(), any());
        }
    }

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: findAllTitles")
    class FindAllTitlesTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD return title list WHEN called")
        void findAllTitles_Success() {
            // Arrange
            var expectedTitleList = StudentTitle.mockList();
            when(studentRepository.selectAllTitles()).thenReturn(expectedTitleList);

            // Act
            var actualTitleList = studentService.findAllTitles();

            // Assert
            assertEquals(expectedTitleList, actualTitleList);

            verify(studentRepository, times(1)).selectAllTitles();
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
        @DisplayName(value = "Scenario 1: SHOULD return profile WHEN id exists")
        void findProfileById_Success() {
            // Arrange
            var expectedProfile = StudentProfile.mock();
            when(studentRepository.selectProfileById(ID)).thenReturn(Optional.of(expectedProfile));

            // Act
            var actualProfile = studentService.findProfileById(ID);

            // Assert
            assertEquals(expectedProfile, actualProfile);

            verify(studentRepository, times(1)).selectProfileById(ID);
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD throw exception WHEN id not found")
        void findProfileById_IdNotFound() {
            // Arrange
            when(studentRepository.selectProfileById(ID)).thenReturn(Optional.empty());

            // Act
            var exception = assertThrows(ResourceNotFoundException.class,
                    () -> studentService.findProfileById(ID));

            // Assert
            assertEquals(StudentMessage.Id.NOT_FOUND, exception.getMessage());

            verify(studentRepository, times(1)).selectProfileById(ID);
        }
    }
}
