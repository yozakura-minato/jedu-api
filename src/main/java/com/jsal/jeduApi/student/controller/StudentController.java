package com.jsal.jeduApi.student.controller;

import com.jsal.jeduApi.common.response.ApiResponseBody;
import com.jsal.jeduApi.security.util.SecurityConstant;
import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.controller.request.UpdateStudentRequest;
import com.jsal.jeduApi.student.repository.projection.StudentProfile;
import com.jsal.jeduApi.student.repository.projection.StudentTitle;
import com.jsal.jeduApi.student.service.StudentApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Student API endpoints")
public class StudentController {
    private final StudentApiService studentApiService;

    @PostMapping(path = "/api/v1/students")
    @Operation(summary = "Create student", description = """
            - Return: Created student ID
            - Validation:
                - Student display ID must not conflict
                - Student's email must not conflict""")
    public ResponseEntity<ApiResponseBody<UUID>> createStudent(@RequestBody @Valid CreateStudentRequest request) {
        UUID currentUserId = SecurityConstant.CURRENT_EMPLOYEE_ID;
        UUID createdStudentId = studentApiService.createStudent(request, currentUserId);
        return ResponseEntity.ok(ApiResponseBody.data(createdStudentId));
    }

    @GetMapping(path = "/api/v1/students/all")
    @Operation(summary = "Find all student titles", description = """
            - Return: Student title list""")
    public ResponseEntity<ApiResponseBody<List<StudentTitle>>> findAllStudentProfiles() {
        List<StudentTitle> studentTitleList = studentApiService.findAllTitles();
        return ResponseEntity.ok(ApiResponseBody.data(studentTitleList));
    }

    @GetMapping(path = "/api/v1/students/id/{studentId}")
    @Operation(summary = "Find student profiles by ID", description = """
            - Return: Student profile
            - Validation: Student ID must exist""")
    public ResponseEntity<ApiResponseBody<StudentProfile>> findStudentProfileByDisplayId(@PathVariable UUID studentId) {
        StudentProfile studentProfile = studentApiService.findStudentProfileById(studentId);
        return ResponseEntity.ok(ApiResponseBody.data(studentProfile));
    }

    @PutMapping(path = "/api/v1/students/id/{studentId}")
    @Operation(summary = "Update student", description = """
            - Return: Student updated time
            - Validation:
                - Student ID must exist
                - Student display ID must not conflict""")
    public ResponseEntity<ApiResponseBody<LocalDateTime>> update(
            @PathVariable UUID studentId, @RequestBody @Valid UpdateStudentRequest request
    ) {
        UUID currentUserId = SecurityConstant.CURRENT_EMPLOYEE_ID;
        LocalDateTime studentUpdatedTime = studentApiService.updateStudent(studentId, request, currentUserId);
        return ResponseEntity.ok(ApiResponseBody.data(studentUpdatedTime));
    }

    @DeleteMapping(path = "/api/v1/students/id/{studentId}")
    @Operation(summary = "Soft delete student", description = """
            - Return: Student soft deleted time
            - Validation: Student ID must exists""")
    public ResponseEntity<ApiResponseBody<LocalDateTime>> delete(@PathVariable UUID studentId) {
        UUID currentUserID = SecurityConstant.CURRENT_EMPLOYEE_ID;
        LocalDateTime studentSoftDeletedTime = studentApiService.softDeleteStudent(studentId, currentUserID);
        return ResponseEntity.ok(ApiResponseBody.data(studentSoftDeletedTime));
    }
}
