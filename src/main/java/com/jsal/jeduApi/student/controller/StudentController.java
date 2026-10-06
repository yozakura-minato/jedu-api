package com.jsal.jeduApi.student.controller;

import com.jsal.jeduApi.common.annotation.TrimmedString;
import com.jsal.jeduApi.common.response.ApiResponse;
import com.jsal.jeduApi.common.util.CommonConstant;
import com.jsal.jeduApi.student.controller.request.CreateStudentRequest;
import com.jsal.jeduApi.student.controller.response.StudentDisplayIdResponse;
import com.jsal.jeduApi.student.repository.projection.StudentProfile;
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

    @PostMapping(path = "/controller/v1/students")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create student", description = """
            * Request body: createRequest - CreateStudentRequest
            * Return data: StudentDisplayIdResponse
            * Return error: when displayId or email is conflicts""")
    public ResponseEntity<ApiResponse<StudentDisplayIdResponse>> create(
            @RequestBody
            @Valid
            CreateStudentRequest createRequest
    ) {
        UUID currentUserId = CommonConstant.CURRENT_EMPLOYEE_ID;
        StudentDisplayIdResponse displayId = studentApiService.create(createRequest, currentUserId);
        return ResponseEntity.ok(ApiResponse.data(displayId));
    }

    @GetMapping(path = "/controller/v1/students/all")
    @Operation(summary = "Find all StudentProfiles", description = """
            * Return data: List of StudentProfile""")
    public ResponseEntity<ApiResponse<List<StudentProfile>>> findAllProfiles() {
        List<StudentProfile> profileList = studentApiService.findAllProfiles();
        return ResponseEntity.ok(ApiResponse.data(profileList));
    }

    @GetMapping(path = "/controller/v1/students/display-id/{displayId}")
    @Operation(summary = "Find StudentProfile by displayId", description = """
            * Path variable: displayId - String
            * Return data: StudentProfile
            * Return error: when displayId is not found""")
    public ResponseEntity<ApiResponse<StudentProfile>> findProfileByDisplayId(
            @PathVariable
            @NotNull(message = StudentMessage.DisplayId.NULL)
            @TrimmedString(message = StudentMessage.DisplayId.NOT_TRIMMED)
            String displayId
    ) {
        StudentProfile profile = studentApiService.findProfileByDisplayId(displayId);
        return ResponseEntity.ok(ApiResponse.data(profile));
    }
}
