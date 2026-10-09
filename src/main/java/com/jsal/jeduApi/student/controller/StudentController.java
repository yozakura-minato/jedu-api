package com.jsal.jeduApi.student.controller;

import com.jsal.jeduApi.common.response.ApiResponse;
import com.jsal.jeduApi.common.util.CommonConstant;
import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.repository.projection.StudentProfile;
import com.jsal.jeduApi.student.repository.projection.StudentTitle;
import com.jsal.jeduApi.student.service.StudentApiService;
import com.jsal.jeduApi.student.util.StudentMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            - Request body: CreateStudentRequest
            - Validation:
                - displayId (not null, trimmed)
                - fullName (not null, trimmed)
                - email (not null, email)
                - phoneNumber (trimmed)
                - password (not null trimmed)
            - Fail when: displayId or email is conflicts, or validation failed""")
    public ResponseEntity<ApiResponse<?>> create(
            @RequestBody
            @Valid
            CreateStudentRequest createRequest
    ) {
        UUID currentUserId = CommonConstant.CURRENT_EMPLOYEE_ID;
        studentApiService.create(createRequest, currentUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(null));
    }

    @GetMapping(path = "/api/v1/students/all")
    @Operation(summary = "Find all StudentTitles", description = """
            - When success: return List of StudentTitle""")
    public ResponseEntity<ApiResponse<List<StudentTitle>>> findAllProfiles() {
        List<StudentTitle> titleList = studentApiService.findAllTitles();
        return ResponseEntity.ok(ApiResponse.success(titleList));
    }

    @GetMapping(path = "/api/v1/students/id/{id}")
    @Operation(summary = "Find StudentProfile by id", description = """
            - Path variable: id - UUID
            - Validation: id (not null)
            - When success: return StudentProfile
            - Fail when: id is not found""")
    public ResponseEntity<ApiResponse<StudentProfile>> findProfileByDisplayId(
            @PathVariable
            @NotNull(message = StudentMessage.Id.NULL)
            UUID id
    ) {
        StudentProfile profile = studentApiService.findProfileById(id);
        return ResponseEntity.ok(ApiResponse.success(profile));
    }
}
