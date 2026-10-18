package com.jsal.jeduApi.student.service.impl;

import com.jsal.jeduApi.common.exception.ResourceConflictException;
import com.jsal.jeduApi.common.exception.ResourceNotFoundException;
import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.controller.request.UpdateStudentRequest;
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

import java.time.LocalDateTime;
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

    private static final UUID STUDENT_ID = UUID.randomUUID();
    private static final UUID CURRENT_USER_ID = UUID.randomUUID();

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: StudentApiService.createStudent")
    class CreateStudentTest {
        private static final CreateStudentRequest REQUEST = new CreateStudentRequest(
                "id4tester",
                "Tester",
                "tester@jedu.dev",
                null,
                "pw4tester"
        );

        @Test
        @DisplayName(value = "Scenario 1: SHOULD return created student ID WHEN success")
        void createStudent_Success() {
            // Arrange
            var uniqueValidation = new StudentUniqueValidation(false, false);
            when(studentRepository.existsStudentByDisplayIdOrEmail(REQUEST.displayId(), REQUEST.email()))
                    .thenReturn(uniqueValidation);

            var expectedCreatedStudentId = UUID.randomUUID();
            when(studentRepository.insertStudent(REQUEST, StudentStatus.CREATED, CURRENT_USER_ID))
                    .thenReturn(expectedCreatedStudentId);

            // Act
            var actualCreatedStudentId = studentService.createStudent(REQUEST, CURRENT_USER_ID);

            // Assert
            assertEquals(expectedCreatedStudentId, actualCreatedStudentId);

            verify(studentRepository, times(1))
                    .existsStudentByDisplayIdOrEmail(REQUEST.displayId(), REQUEST.email());
            verify(studentRepository, times(1))
                    .insertStudent(REQUEST, StudentStatus.CREATED, CURRENT_USER_ID);
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD throw exception WHEN display ID conflicts")
        void create_DisplayIdConflict() {
            // Arrange
            var studentUniqueValidation = new StudentUniqueValidation(true, false);
            when(studentRepository.existsStudentByDisplayIdOrEmail(REQUEST.displayId(), REQUEST.email()))
                    .thenReturn(studentUniqueValidation);

            // Act
            var exception = assertThrows(ResourceConflictException.class,
                    () -> studentService.createStudent(REQUEST, CURRENT_USER_ID));

            // Assert
            assertEquals(StudentMessage.DisplayId.CONFLICT, exception.getMessage());

            verify(studentRepository, times(1))
                    .existsStudentByDisplayIdOrEmail(REQUEST.displayId(), REQUEST.email());
            verify(studentRepository, never()).insertStudent(any(), any(), any());
        }

        @Test
        @DisplayName(value = "Scenario 3: SHOULD throw exception WHEN email conflicts")
        void createStudent_EmailConflict() {
            // Arrange
            var studentUniqueValidation = new StudentUniqueValidation(false, true);
            when(studentRepository.existsStudentByDisplayIdOrEmail(REQUEST.displayId(), REQUEST.email()))
                    .thenReturn(studentUniqueValidation);

            // Act
            var exception = assertThrows(ResourceConflictException.class,
                    () -> studentService.createStudent(REQUEST, CURRENT_USER_ID));

            // Assert
            assertEquals(StudentMessage.Email.CONFLICT, exception.getMessage());

            verify(studentRepository, times(1))
                    .existsStudentByDisplayIdOrEmail(REQUEST.displayId(), REQUEST.email());
            verify(studentRepository, never()).insertStudent(any(), any(), any());
        }
    }

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: StudentApiService.findAllStudentTitles")
    class FindAllStudentTitlesTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD return student title list WHEN success")
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
    @DisplayName(value = "Test for: StudentApiService.findStudentProfileById")
    class FindStudentProfileByIdTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD return student profile WHEN success")
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
        @DisplayName(value = "Scenario 2: SHOULD throw exception WHEN ID not found")
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

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: StudentApiService.updateStudent")
    class UpdateStudentTest {
        private static final UpdateStudentRequest REQUEST = new UpdateStudentRequest(
                "STD1",
                "Student One",
                null,
                StudentStatus.ACTIVE
        );

        @Test
        @DisplayName(value = "Scenario 1: SHOULD return student updated time WHEN success")
        void updateStudent_Success() {
            // Arrange
            when(studentRepository.existsStudentById(STUDENT_ID)).thenReturn(true);

            when(studentRepository.existsStudentByDisplayAndIdIsNot(REQUEST.displayId(), STUDENT_ID))
                    .thenReturn(false);

            var expectedStudentUpdatedTime = LocalDateTime.now();
            when(studentRepository.updateStudent(STUDENT_ID, REQUEST, CURRENT_USER_ID))
                    .thenReturn(expectedStudentUpdatedTime);

            // Act
            var actualStudentUpdatedTime = studentService.updateStudent(STUDENT_ID, REQUEST, CURRENT_USER_ID);

            // Assert
            assertEquals(expectedStudentUpdatedTime, actualStudentUpdatedTime);

            verify(studentRepository, times(1)).existsStudentById(STUDENT_ID);
            verify(studentRepository, times(1))
                    .existsStudentByDisplayAndIdIsNot(REQUEST.displayId(), STUDENT_ID);
            verify(studentRepository, times(1))
                    .updateStudent(STUDENT_ID, REQUEST, CURRENT_USER_ID);
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD throw exception WHEN student ID not found")
        void updateStudent_IdNotFound() {
            // Arrange
            when(studentRepository.existsStudentById(STUDENT_ID)).thenReturn(false);

            // Act
            var exception = assertThrows(ResourceNotFoundException.class,
                    () -> studentService.updateStudent(STUDENT_ID, REQUEST, CURRENT_USER_ID));

            // Assert
            assertEquals(StudentMessage.Id.NOT_FOUND, exception.getMessage());

            verify(studentRepository, times(1)).existsStudentById(STUDENT_ID);
            verify(studentRepository, never()).existsStudentByDisplayAndIdIsNot(any(), any());
            verify(studentRepository, never()).updateStudent(any(), any(), any());
        }

        @Test
        @DisplayName(value = "Scenario 3: SHOULD throw exception WHEN student display ID conflicts")
        void updateStudent_DisplayIdConflicts() {
            // Arrange
            when(studentRepository.existsStudentById(STUDENT_ID)).thenReturn(true);

            when(studentRepository.existsStudentByDisplayAndIdIsNot(REQUEST.displayId(), STUDENT_ID))
                    .thenReturn(true);

            // Act
            var exception = assertThrows(ResourceConflictException.class,
                    () -> studentService.updateStudent(STUDENT_ID, REQUEST, CURRENT_USER_ID));

            // Assert
            assertEquals(StudentMessage.DisplayId.CONFLICT, exception.getMessage());

            verify(studentRepository, times(1)).existsStudentById(STUDENT_ID);
            verify(studentRepository, times(1))
                    .existsStudentByDisplayAndIdIsNot(REQUEST.displayId(), STUDENT_ID);
            verify(studentRepository, never()).updateStudent(any(), any(), any());
        }
    }

    /**
     * @author Yozakura Minato
     */
    @Nested
    @DisplayName(value = "Test for: StudentApiService.softDeleteStudent")
    class SoftDeleteStudentTest {
        @Test
        @DisplayName(value = "Scenario 1: SHOULD return student soft deleted time WHEN success")
        void softDeleteStudent_Success() {
            // Arrange
            when(studentRepository.existsStudentById(STUDENT_ID)).thenReturn(true);

            var expectedStudentSoftDeletedTime = LocalDateTime.now();
            when(studentRepository.softDeleteStudent(STUDENT_ID, CURRENT_USER_ID))
                    .thenReturn(expectedStudentSoftDeletedTime);

            // Act
            var actualStudentSoftDeletedTime = studentService.softDeleteStudent(STUDENT_ID, CURRENT_USER_ID);

            // Assert
            assertEquals(expectedStudentSoftDeletedTime, actualStudentSoftDeletedTime);

            verify(studentRepository, times(1)).existsStudentById(STUDENT_ID);
            verify(studentRepository, timeout(1)).softDeleteStudent(STUDENT_ID, CURRENT_USER_ID);
        }

        @Test
        @DisplayName(value = "Scenario 2: SHOULD throw exception WHEN student ID not found")
        void softDeleteStudent_IdNotFound() {
            // Arrange
            when(studentRepository.existsStudentById(STUDENT_ID)).thenReturn(false);

            // Act
            var exception = assertThrows(ResourceNotFoundException.class,
                    () -> studentService.softDeleteStudent(STUDENT_ID, CURRENT_USER_ID));

            // Assert
            assertEquals(StudentMessage.Id.NOT_FOUND, exception.getMessage());

            verify(studentRepository, timeout(1)).existsStudentById(STUDENT_ID);
            verify(studentRepository, never()).softDeleteStudent(any(), any());
        }
    }
}
