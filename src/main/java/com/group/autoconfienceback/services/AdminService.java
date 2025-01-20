package com.group.autoconfienceback.services;

import com.group.autoconfienceback.dto.*;
import com.group.autoconfienceback.dto.account_management.EmployeeDetailsResponse;
import com.group.autoconfienceback.dto.account_management.EmployeeAccountDto;
import com.group.autoconfienceback.dto.account_management.UpdateAdminAccount;
import com.group.autoconfienceback.dto.account_management.UpdateEmployeeDto;
import com.group.autoconfienceback.entities.user_entities.Admin;
import com.group.autoconfienceback.entities.user_entities.Employee;
import com.group.autoconfienceback.exceptions.ApiException;
import com.group.autoconfienceback.mappers.AccountManagementMapper;
import com.group.autoconfienceback.repositories.user_repositories.AdminRepository;
import com.group.autoconfienceback.repositories.user_repositories.EmployeeRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;
    private final AccountManagementMapper accountManagementMapper;
    private final UtilityService utilityService;

    @Autowired
    public AdminService(AdminRepository adminRepository, EmployeeRepository employeeRepository, EmailService emailService, AccountManagementMapper accountManagementMapper, UtilityService utilityService) {
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
        this.accountManagementMapper = accountManagementMapper;
        this.utilityService = utilityService;
    }

    public ResponseEntity<ApiResponse<String>> createEmployee(EmployeeAccountDto employeeData) throws MessagingException {
        Optional<Employee> employee = employeeRepository.findByEmail(employeeData.getEmail());

        if (employee.isPresent()) throw new ApiException("Email is already in use", 409);

        Employee newEmployee = accountManagementMapper.createEmployeeFromDto(employeeData);

        employeeRepository.save(newEmployee);

        emailService.sendAccountInformations(newEmployee.getEmail(), employeeData.getPassword());

        return ResponseEntity.ok(new ApiResponse<>("the employee has been created successfully"));
    }

    public ResponseEntity<ApiResponse<List<EmployeeDetailsResponse>>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        List<EmployeeDetailsResponse> employeesDto = accountManagementMapper.createEmployeeListFromEmployees(employees);

        return ResponseEntity.status(200).body(new ApiResponse<>("Employees list", employeesDto));
    }

    public ResponseEntity<ApiResponse<String>> deleteEmployee(String email) {
        Employee employee = utilityService.findEmployeeByEmail(email);

        employeeRepository.delete(employee);

        return ResponseEntity.status(200).body(new ApiResponse<>("employee has been deleted"));
    }

    public ResponseEntity<ApiResponse<String>> updateEmployee(UpdateEmployeeDto employeeData) {
        Employee employee = utilityService.findEmployeeByEmail(employeeData.getEmail());

        employee.setName(employeeData.getName());
        employee.setLastName(employeeData.getLastName());
        employee.setAddress(employeeData.getAddress());
        employee.setBirthDate(employeeData.getBirthDate());
        employee.setPoste(employeeData.getPoste());

        employeeRepository.save(employee);

        return ResponseEntity.status(200).body(new ApiResponse<>("employee has been updated"));
    }

    public ResponseEntity<ApiResponse<String>> updateAccount(UpdateAdminAccount adminData) {
        Admin admin = utilityService.findAdminByEmail(adminData.getEmail());

        admin.setName(adminData.getName());
        admin.setLastName(adminData.getLastName());
        admin.setAddress(adminData.getAddress());

        adminRepository.save(admin);

        return ResponseEntity.status(200).body(new ApiResponse<>("admin has been updated"));
    }

}