package com.jsal.jeduApi.employee.controller;

import com.jsal.jeduApi.common.response.ApiResponseBody;
import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;
import com.jsal.jeduApi.employee.repository.projection.EmployeeTitle;
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

import java.util.List;
import java.util.UUID;

/**
 * @author Yozakura Minato
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Employee API endpoints")
public class EmployeeController {
    private final EmployeeApiService employeeApiService;

    @GetMapping(path = "/api/v1/employees/all")
    @Operation(summary = "Find all EmployeeTitle", description = """
            * When success: return EmployeeTitle List""")
    public ResponseEntity<ApiResponseBody<List<EmployeeTitle>>> findAllEmployeeTitles() {
        List<EmployeeTitle> employeeTitleList = employeeApiService.findAllEmployeeTitles();
        return ResponseEntity.ok(ApiResponseBody.data(employeeTitleList));
    }

    @GetMapping(path = "/api/v1/employees/id/{id}")
    @Operation(summary = "Find EmployeeProfile by id", description = """
            * Path variable: id - UUID
            * Validation: id (not null)
            * When success: return EmployeeProfile
            * Fail when: id is not found or validation failed""")
    public ResponseEntity<ApiResponseBody<EmployeeProfile>> findEmployeeProfileById(
            @PathVariable
            @NotNull(message = EmployeeMessage.Id.NULL)
            UUID id
    ) {
        EmployeeProfile employeeProfile = employeeApiService.findEmployeeProfileById(id);
        return ResponseEntity.ok(ApiResponseBody.data(employeeProfile));
    }
}
