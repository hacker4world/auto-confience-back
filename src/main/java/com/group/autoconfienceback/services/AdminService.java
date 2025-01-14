package com.group.autoconfienceback.services;


import com.group.autoconfienceback.dto.*;
import com.group.autoconfienceback.entities.Admin;
import com.group.autoconfienceback.entities.Employee;
import com.group.autoconfienceback.entities.User;
import com.group.autoconfienceback.repositories.AdminRepository;
import com.group.autoconfienceback.repositories.EmployeeRepository;
import com.group.autoconfienceback.repositories.UserRepository;
import jakarta.mail.MessagingException;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, EmployeeRepository employeeRepository, EmailService emailService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public ResponseEntity<ApiResponse<String>> createEmployee(EmployeeDto employeeData) throws MessagingException {

        Optional<Employee> employee = employeeRepository.findByEmail(employeeData.getEmail());

        if (employee.isPresent()) {
            return ResponseEntity.status(400).body(new ApiResponse<>("the email is already used"));
        }

        Employee newEmployee = new Employee(
                employeeData.getName(),
                employeeData.getLastName(),
                employeeData.getAddress(),
                employeeData.getEmail(),
                passwordEncoder.encode(employeeData.getPassword()),
                employeeData.getBirthDate(),
                employeeData.getPoste()
        );

        employeeRepository.save(newEmployee);

        emailService.sendAccountInformations(newEmployee.getEmail(), newEmployee.getPassword());

        return ResponseEntity.ok(new ApiResponse<>("the employee has been created successfully"));


    }

    public ResponseEntity<ApiResponse<String>> deleteEmployee(String email) {

        Optional<Employee> employee = employeeRepository.findByEmail(email);

        if (employee.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse<>("the employee with the given email does not exist"));
        }

        employeeRepository.delete(employee.get());

        return ResponseEntity.status(200).body(new ApiResponse<>("employee has been deleted"));

    }


    public ResponseEntity<ApiResponse<String>> updateEmployee(UpdateEmployeeDto employeeData) {
        Optional<Employee> employee = employeeRepository.findByEmail(employeeData.getEmail());

        if (employee.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse<>("the employee with the given email does not exist"));
        }
        Employee updateEmployee = employee.get();
        updateEmployee.setName(employeeData.getName());
        updateEmployee.setLastName(employeeData.getLastName());
        updateEmployee.setAddress(employeeData.getAddress());
        updateEmployee.setBirthDate(employeeData.getBirthDate());
        updateEmployee.setPoste(employeeData.getPoste());

        employeeRepository.save(updateEmployee);

        return ResponseEntity.status(200).body(new ApiResponse<>("employee has been updated"));
    }

    public ResponseEntity<ApiResponse<String>> updateAccount(UpdateAdminAccount adminData) {
        Optional<Admin> admin = adminRepository.findByEmail(adminData.getEmail());

        if (admin.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse<>("the admin with the given email does not exist"));
        }

        Admin updateAdmin = admin.get();

        updateAdmin.setName(adminData.getName());
        updateAdmin.setLastName(adminData.getLastName());
        updateAdmin.setAddress(adminData.getAddress());

        adminRepository.save(updateAdmin);

        return ResponseEntity.status(200).body(new ApiResponse<>("admin has been updated"));

    }

}