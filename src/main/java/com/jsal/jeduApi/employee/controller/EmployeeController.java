package com.jsal.jeduApi.employee.controller;

import com.jsal.jeduApi.common.response.ApiResponseBody;
import com.jsal.jeduApi.employee.repository.projection.EmployeeProfile;
import com.jsal.jeduApi.employee.repository.projection.EmployeeTitle;
import com.jsal.jeduApi.employee.service.EmployeeApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Operation(summary = "Find all employee titles", description = """
            - Return: Employee title list""")
    public ResponseEntity<ApiResponseBody<List<EmployeeTitle>>> findAllEmployeeTitles() {
        List<EmployeeTitle> employeeTitleList = employeeApiService.findAllEmployeeTitles();
        return ResponseEntity.ok(ApiResponseBody.data(employeeTitleList));
    }

    @GetMapping(path = "/api/v1/employees/id/{employeeId}")
    @Operation(summary = "Find employee profile by ID", description = """
            - Return: Employee profile
            - Validation: Employee ID must exists""")
    public ResponseEntity<ApiResponseBody<EmployeeProfile>> findEmployeeProfileById(@PathVariable UUID employeeId) {
        EmployeeProfile employeeProfile = employeeApiService.findEmployeeProfileById(employeeId);
        return ResponseEntity.ok(ApiResponseBody.data(employeeProfile));
    }
}
