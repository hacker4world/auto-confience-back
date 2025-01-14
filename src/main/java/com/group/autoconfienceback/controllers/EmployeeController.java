package com.group.autoconfienceback.controllers;

import com.group.autoconfienceback.dto.ApiResponse;
import com.group.autoconfienceback.dto.UpdateEmployeeAccount;
import com.group.autoconfienceback.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PutMapping("update-account")
    public ResponseEntity<ApiResponse<String>> updateEmployeeAccount(@RequestBody UpdateEmployeeAccount employeeData) {
        return employeeService.updateEmployeeAccount(employeeData);
    }
}
