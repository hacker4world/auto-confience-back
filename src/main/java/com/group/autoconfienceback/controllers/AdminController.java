package com.group.autoconfienceback.controllers;

import com.group.autoconfienceback.dto.EmployeeDto;
import com.group.autoconfienceback.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Api/Admin")
public class AdminController {
    private final AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @DeleteMapping("deleteEmployee/{id}")
    public ResponseEntity<String>deleteEmployee(@PathVariable("id")int id ){
        adminService.deleteEmployee(id);
        return ResponseEntity.ok("employee has been deleted successfully");

    }
    @PutMapping("CreateEmployee")
    public ResponseEntity<String> CreateEmployee(@RequestBody EmployeeDto employeeData){
        return adminService.CreateEmployee(employeeData);
    }
    @PutMapping("updateEmployee/{id}")
    public ResponseEntity<String>updateEmployee(@PathVariable("id")int id,@RequestBody  EmployeeDto employeeData){
        return adminService.updateEmployee(id,employeeData);
    }
}
