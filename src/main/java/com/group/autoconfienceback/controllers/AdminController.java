package com.group.autoconfienceback.controllers;

import com.group.autoconfienceback.dto.*;
import com.group.autoconfienceback.dto.account_management.EmployeeDetailsResponse;
import com.group.autoconfienceback.dto.account_management.EmployeeAccountDto;
import com.group.autoconfienceback.dto.account_management.UpdateAdminAccount;
import com.group.autoconfienceback.dto.account_management.UpdateEmployeeDto;
import com.group.autoconfienceback.services.AdminService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @DeleteMapping("deleteEmployee/{email}")
    public ResponseEntity<ApiResponse<String>>deleteEmployee(@Valid @PathVariable("email") String email){
        return adminService.deleteEmployee(email);
    }

    @PostMapping("create-employee-account")
    public ResponseEntity<ApiResponse<String>> createEmployee(@Valid @RequestBody EmployeeAccountDto employeeData) throws MessagingException {
        return adminService.createEmployee(employeeData);
    }

    @PutMapping("update-employee")
    public ResponseEntity<ApiResponse<String>>updateEmployee(@Valid @RequestBody UpdateEmployeeDto employeeData){
        return adminService.updateEmployee(employeeData);
    }

    @PutMapping("update-account")
    public ResponseEntity<ApiResponse<String>>updateAccount(@Valid @RequestBody UpdateAdminAccount employeeData){
        return adminService.updateAccount(employeeData);
    }

    @GetMapping("employees-list")
    public ResponseEntity<ApiResponse<List<EmployeeDetailsResponse>>>getEmployees(){
        return adminService.getAllEmployees();
    }

}
