package com.jsal.jeduApi.student.controller;

import com.jsal.jeduApi.common.response.ApiResponseBody;
import com.jsal.jeduApi.security.util.SecurityConstant;
import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.controller.request.UpdateStudentRequest;
import com.jsal.jeduApi.student.repository.projection.StudentDisplayTitle;
import com.jsal.jeduApi.student.repository.projection.StudentProfile;
import com.jsal.jeduApi.student.repository.projection.StudentTitle;
import com.jsal.jeduApi.student.service.StudentApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
@RestController
@Tag(name = "Student API endpoints")
public class StudentController {
    private final StudentApiService studentApiService;

    public StudentController(StudentApiService studentApiService) {
        this.studentApiService = studentApiService;
    }

    @PostMapping(path = "/api/v1/students")
    @Operation(summary = "Create student", description = """
            - Return: Created student UUID
            - Validation:
                - Student displayId must not conflict
                - Student email must not conflict
            """)
    public ResponseEntity<ApiResponseBody<UUID>> createStudent(@RequestBody @Valid CreateStudentRequest request) {
        UUID currentUserId = SecurityConstant.CURRENT_EMPLOYEE_ID;
        UUID createdStudentId = studentApiService.createStudent(request, currentUserId);
        return ResponseEntity.ok(ApiResponseBody.data(createdStudentId));
    }

    @GetMapping(path = "/api/v1/students/all")
    @Operation(summary = "Find all student titles", description = """
            - Return: List of StudentTitle
            """)
    public ResponseEntity<ApiResponseBody<List<StudentTitle>>> findAllStudentProfiles() {
        List<StudentTitle> studentTitleList = studentApiService.findAllTitles();
        return ResponseEntity.ok(ApiResponseBody.data(studentTitleList));
    }

    @GetMapping(path = "/api/v1/students/id/{studentId}")
    @Operation(summary = "Find student profile by ID", description = """
            - Return: StudentProfile
            - Validation: Student id must exist
            """)
    public ResponseEntity<ApiResponseBody<StudentProfile>> findStudentProfileByDisplayId(@PathVariable UUID studentId) {
        StudentProfile studentProfile = studentApiService.findStudentProfileById(studentId);
        return ResponseEntity.ok(ApiResponseBody.data(studentProfile));
    }

    @PutMapping(path = "/api/v1/students/id/{studentId}")
    @Operation(summary = "Update student", description = """
            - Validation:
                - Student id must exist
                - Student displayId must not conflict
            """)
    public ResponseEntity<ApiResponseBody<LocalDateTime>> update(
            @PathVariable UUID studentId, @RequestBody @Valid UpdateStudentRequest request
    ) {
        UUID currentUserId = SecurityConstant.CURRENT_EMPLOYEE_ID;
        studentApiService.updateStudent(studentId, request, currentUserId);
        return ResponseEntity.ok(ApiResponseBody.empty());
    }

    @DeleteMapping(path = "/api/v1/students/id/{studentId}")
    @Operation(summary = "Soft delete student", description = """
            - Return: Soft deleted StudentTitle
            - Validation: Student id must exist
            """)
    public ResponseEntity<ApiResponseBody<StudentDisplayTitle>> softDelete(@PathVariable UUID studentId) {
        UUID currentUserID = SecurityConstant.CURRENT_EMPLOYEE_ID;
        StudentDisplayTitle softDeleteStudentTitle = studentApiService.softDeleteStudent(studentId, currentUserID);
        return ResponseEntity.ok(ApiResponseBody.data(softDeleteStudentTitle));
    }
}
