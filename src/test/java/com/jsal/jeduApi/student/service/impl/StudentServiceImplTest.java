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
class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: createStudent")
    class CreateTest {
        private static final CreateStudentRequest CREATE_REQUEST = new CreateStudentRequest(
                "id4tester",
                "Tester",
                "tester@jedu.dev",
                null,
                "pw4tester"
        );
        private static final UUID CURRENT_USER_ID = UUID.randomUUID();

        private static final String DISPLAY_ID = CREATE_REQUEST.displayId();
        private static final String EMAIL = CREATE_REQUEST.email();

        @Test
        @DisplayName(value = "Scenario 1: SHOULD return id WHEN request is valid")
        void create_Success() {
            // Arrange
            var uniqueValidation = new StudentUniqueValidation(false, false);
            when(studentRepository.existsStudentByDisplayIdOrEmail(DISPLAY_ID, EMAIL))
                    .thenReturn(uniqueValidation);

            var expectedId = UUID.randomUUID();
            when(studentRepository.insertStudent(CREATE_REQUEST, StudentStatus.CREATED, CURRENT_USER_ID))
                    .thenReturn(expectedId);

            // Act
            var actualId = studentService.createStudent(CREATE_REQUEST, CURRENT_USER_ID);

            // Assert
            assertEquals(expectedId, actualId);

            verify(studentRepository, times(1)).existsStudentByDisplayIdOrEmail(DISPLAY_ID, EMAIL);
            verify(studentRepository, times(1))
                    .insertStudent(CREATE_REQUEST, StudentStatus.CREATED, CURRENT_USER_ID);
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD throw exception WHEN displayId conflicts")
        void create_DisplayIdConflict() {
            // Arrange
            var uniqueValidation = new StudentUniqueValidation(true, false);
            when(studentRepository.existsStudentByDisplayIdOrEmail(DISPLAY_ID, EMAIL)).thenReturn(uniqueValidation);

            // Act
            var exception = assertThrows(ResourceConflictException.class,
                    () -> studentService.createStudent(CREATE_REQUEST, CURRENT_USER_ID));

            // Assert
            assertEquals(StudentMessage.DisplayId.CONFLICT, exception.getMessage());

            verify(studentRepository, times(1)).existsStudentByDisplayIdOrEmail(DISPLAY_ID, EMAIL);
            verify(studentRepository, never()).insertStudent(any(), any(), any());
        }

        @Test
        @DisplayName(value = "Scenario 3: SHOULD throw exception WHEN email conflicts")
        void create_EmailConflict() {
            // Arrange
            var uniqueValidation = new StudentUniqueValidation(false, true);
            when(studentRepository.existsStudentByDisplayIdOrEmail(DISPLAY_ID, EMAIL)).thenReturn(uniqueValidation);

            // Act
            var exception = assertThrows(ResourceConflictException.class,
                    () -> studentService.createStudent(CREATE_REQUEST, CURRENT_USER_ID));

            // Assert
            assertEquals(StudentMessage.Email.CONFLICT, exception.getMessage());

            verify(studentRepository, times(1)).existsStudentByDisplayIdOrEmail(DISPLAY_ID, EMAIL);
            verify(studentRepository, never()).insertStudent(any(), any(), any());
        }

        @Test
        @DisplayName(value = "Scenario 4: SHOULD throw exception WHEN both displayId and email conflict")
        void create_DisplayIdAndEmailConflict() {
            // Arrange
            var uniqueValidation = new StudentUniqueValidation(true, true);
            when(studentRepository.existsStudentByDisplayIdOrEmail(DISPLAY_ID, EMAIL)).thenReturn(uniqueValidation);

            // Act
            var exception = assertThrows(ResourceConflictException.class,
                    () -> studentService.createStudent(CREATE_REQUEST, CURRENT_USER_ID));

            // Assert
            assertEquals(StudentMessage.DisplayId.CONFLICT, exception.getMessage());

            verify(studentRepository, times(1)).existsStudentByDisplayIdOrEmail(DISPLAY_ID, EMAIL);
            verify(studentRepository, never()).insertStudent(any(), any(), any());
        }
    }

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: findAllEmployeeTitles")
    class FindAllTitlesTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD return title list WHEN called")
        void findAllTitles_Success() {
            // Arrange
            var expectedTitleList = List.of(
                    new StudentTitle(UUID.randomUUID(), "std1", "Student 1"),
                    new StudentTitle(UUID.randomUUID(), "std2", "Student 2"),
                    new StudentTitle(UUID.randomUUID(), "std3", "Student 3")
            );
            when(studentRepository.selectAllStudentTitles()).thenReturn(expectedTitleList);

            // Act
            var actualTitleList = studentService.findAllTitles();

            // Assert
            assertEquals(expectedTitleList, actualTitleList);

            verify(studentRepository, times(1)).selectAllStudentTitles();
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
        @DisplayName(value = "Scenario 1: SHOULD return profile WHEN id exists")
        void findProfileById_Success() {
            // Arrange
            var expectedProfile = new StudentProfile(
                    UUID.randomUUID(),
                    "STD1",
                    "Student 1",
                    "std1@jedu.dev",
                    null,
                    StudentStatus.ACTIVE
            );
            when(studentRepository.selectStudentProfileById(ID)).thenReturn(Optional.of(expectedProfile));

            // Act
            var actualProfile = studentService.findStudentProfileById(ID);

            // Assert
            assertEquals(expectedProfile, actualProfile);

            verify(studentRepository, times(1)).selectStudentProfileById(ID);
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD throw exception WHEN id not found")
        void findProfileById_IdNotFound() {
            // Arrange
            when(studentRepository.selectStudentProfileById(ID)).thenReturn(Optional.empty());

            // Act
            var exception = assertThrows(ResourceNotFoundException.class,
                    () -> studentService.findStudentProfileById(ID));

            // Assert
            assertEquals(StudentMessage.Id.NOT_FOUND, exception.getMessage());

            verify(studentRepository, times(1)).selectStudentProfileById(ID);
        }
    }
}
