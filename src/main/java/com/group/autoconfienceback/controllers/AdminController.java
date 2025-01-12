package com.group.autoconfienceback.controllers;

import com.group.autoconfienceback.dto.EmployeeDto;
import com.group.autoconfienceback.dto.UpdateEmployeeDto;
import com.group.autoconfienceback.services.AdminService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @DeleteMapping("deleteEmployee/{id}")
    public ResponseEntity<String>deleteEmployee(@PathVariable("id") int id){
        return adminService.deleteEmployee(id);
    }

    @PostMapping("create-employee-account")
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeDto employeeData) throws MessagingException {
        return adminService.createEmployee(employeeData);
    }

    @PutMapping("update-employee/{id}")
    public ResponseEntity<String>updateEmployee(@PathVariable("id") int id, @RequestBody UpdateEmployeeDto employeeData){
        return adminService.updateEmployee(id,employeeData);
    }
}
