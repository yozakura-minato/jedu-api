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
    class CreateStudentTest {
        private static final CreateStudentRequest CREATE_STUDENT_REQUEST = new CreateStudentRequest(
                "id4tester",
                "Tester",
                "tester@jedu.dev",
                null,
                "pw4tester"
        );
        private static final UUID CURRENT_USER_ID = UUID.randomUUID();

        private static final String DISPLAY_ID = CREATE_STUDENT_REQUEST.displayId();
        private static final String EMAIL = CREATE_STUDENT_REQUEST.email();

        @Test
        @DisplayName(value = "Scenario 1: SHOULD return created student id WHEN create request is valid")
        void createStudent_Success() {
            // Arrange
            var uniqueValidation = new StudentUniqueValidation(false, false);
            when(studentRepository.existsStudentByDisplayIdOrEmail(DISPLAY_ID, EMAIL)).thenReturn(uniqueValidation);

            var expectedCreatedStudentId = UUID.randomUUID();
            when(studentRepository.insertStudent(CREATE_STUDENT_REQUEST, StudentStatus.CREATED, CURRENT_USER_ID))
                    .thenReturn(expectedCreatedStudentId);

            // Act
            var actualCreatedStudentId = studentService.createStudent(CREATE_STUDENT_REQUEST, CURRENT_USER_ID);

            // Assert
            assertEquals(expectedCreatedStudentId, actualCreatedStudentId);

            verify(studentRepository, times(1))
                    .existsStudentByDisplayIdOrEmail(DISPLAY_ID, EMAIL);
            verify(studentRepository, times(1))
                    .insertStudent(CREATE_STUDENT_REQUEST, StudentStatus.CREATED, CURRENT_USER_ID);
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD throw exception WHEN display id conflicts")
        void create_DisplayIdConflict() {
            // Arrange
            var studentUniqueValidation = new StudentUniqueValidation(true, false);
            when(studentRepository.existsStudentByDisplayIdOrEmail(DISPLAY_ID, EMAIL))
                    .thenReturn(studentUniqueValidation);

            // Act
            var exception = assertThrows(ResourceConflictException.class,
                    () -> studentService.createStudent(CREATE_STUDENT_REQUEST, CURRENT_USER_ID));

            // Assert
            assertEquals(StudentMessage.DisplayId.CONFLICT, exception.getMessage());

            verify(studentRepository, times(1))
                    .existsStudentByDisplayIdOrEmail(DISPLAY_ID, EMAIL);
            verify(studentRepository, never()).insertStudent(any(), any(), any());
        }

        @Test
        @DisplayName(value = "Scenario 3: SHOULD throw exception WHEN email conflicts")
        void createStudent_EmailConflict() {
            // Arrange
            var studentUniqueValidation = new StudentUniqueValidation(false, true);
            when(studentRepository.existsStudentByDisplayIdOrEmail(DISPLAY_ID, EMAIL))
                    .thenReturn(studentUniqueValidation);

            // Act
            var exception = assertThrows(ResourceConflictException.class,
                    () -> studentService.createStudent(CREATE_STUDENT_REQUEST, CURRENT_USER_ID));

            // Assert
            assertEquals(StudentMessage.Email.CONFLICT, exception.getMessage());

            verify(studentRepository, times(1))
                    .existsStudentByDisplayIdOrEmail(DISPLAY_ID, EMAIL);
            verify(studentRepository, never()).insertStudent(any(), any(), any());
        }
    }

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: findAllStudentTitles")
    class FindAllStudentTitlesTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD return student title list")
        void findAllTitles_Success() {
            // Arrange
            var expectedStudentTitleList = List.of(
                    new StudentTitle(UUID.randomUUID(), "std1", "Student 1"),
                    new StudentTitle(UUID.randomUUID(), "std2", "Student 2"),
                    new StudentTitle(UUID.randomUUID(), "std3", "Student 3")
            );
            when(studentRepository.selectAllStudentTitles()).thenReturn(expectedStudentTitleList);

            // Act
            var actualStudentTitleList = studentService.findAllTitles();

            // Assert
            assertEquals(expectedStudentTitleList, actualStudentTitleList);

            verify(studentRepository, times(1)).selectAllStudentTitles();
        }
    }

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: findStudentProfileById")
    class FindStudentProfileByIdTest {
        private static final UUID STUDENT_ID = UUID.randomUUID();

        @Test
        @DisplayName(value = "Scenario 1: SHOULD return student profile WHEN id exists")
        void findStudentProfileById_Success() {
            // Arrange
            var expectedStudentProfile = new StudentProfile(
                    UUID.randomUUID(),
                    "STD1",
                    "Student 1",
                    "std1@jedu.dev",
                    null,
                    StudentStatus.ACTIVE
            );
            when(studentRepository.selectStudentProfileById(STUDENT_ID))
                    .thenReturn(Optional.of(expectedStudentProfile));

            // Act
            var actualStudentProfile = studentService.findStudentProfileById(STUDENT_ID);

            // Assert
            assertEquals(expectedStudentProfile, actualStudentProfile);

            verify(studentRepository, times(1)).selectStudentProfileById(STUDENT_ID);
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD throw exception WHEN is not found")
        void findStudentProfileById_IdNotFound() {
            // Arrange
            when(studentRepository.selectStudentProfileById(STUDENT_ID)).thenReturn(Optional.empty());

            // Act
            var exception = assertThrows(ResourceNotFoundException.class,
                    () -> studentService.findStudentProfileById(STUDENT_ID));

            // Assert
            assertEquals(StudentMessage.Id.NOT_FOUND, exception.getMessage());

            verify(studentRepository, times(1)).selectStudentProfileById(STUDENT_ID);
        }
    }
}
