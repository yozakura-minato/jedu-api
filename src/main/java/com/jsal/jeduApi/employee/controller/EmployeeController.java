package com.jsal.jeduApi.employee.controller;

import com.jsal.jeduApi.common.annotation.TrimmedString;
import com.jsal.jeduApi.common.response.ApiResponse;
import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;
import com.jsal.jeduApi.employee.service.EmployeeApiService;
import com.jsal.jeduApi.employee.util.EmployeeMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yozakura Minato
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Employee API endpoints")
public class EmployeeController {
    private final EmployeeApiService employeeApiService;

    @GetMapping(path = "/controller/v1/employees/display-id/{displayId}")
    @Operation(summary = "Find EmployeeProfile by displayId", description = """
            * Path variable: displayId - String
            * Return data: EmployeeProfile
            * Return error: when displayId not found"""
    )
    public ResponseEntity<ApiResponse<EmployeeProfile>> findEmployeeProfileByDisplayId(
            @PathVariable
            @NotNull(message = EmployeeMessage.DisplayId.NULL)
            @TrimmedString(message = EmployeeMessage.DisplayId.NOT_TRIMMED)
            String displayId
    ) {
        EmployeeProfile profile = employeeApiService.findProfileByDisplayId(displayId);
        return ResponseEntity.ok(ApiResponse.data(profile));
    }
}
