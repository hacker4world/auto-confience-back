package com.group.autoconfienceback.services;


import com.group.autoconfienceback.dto.EmployeeDto;
import com.group.autoconfienceback.dto.UpdateEmployeeDto;
import com.group.autoconfienceback.entities.Employee;
import com.group.autoconfienceback.repositories.AdminRepository;
import com.group.autoconfienceback.repositories.EmployeeRepository;
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

    @Autowired
    public AdminService(AdminRepository adminRepository, EmployeeRepository employeeRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> createEmployee(EmployeeDto employeeData) throws MessagingException {

        Optional<Employee> employee = employeeRepository.findByEmail(employeeData.getEmail());

        if (employee.isPresent()) {
            return ResponseEntity.status(400).body("the employee with the email : " + employeeData.getEmail() + " already exists.");
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

        return ResponseEntity.ok("Employee has been added successfully");


    }

    public ResponseEntity<String> deleteEmployee(int id) {

        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isEmpty()) {
            return ResponseEntity.status(404).body("the employee with the id :" + id + " does not exist.");
        }

        employeeRepository.delete(employee.get());

        return ResponseEntity.status(200).body("the employee with the id :" + id + " has been deleted");

    }


    public ResponseEntity<String> updateEmployee(int id, UpdateEmployeeDto employeeData) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isEmpty()) {
            return ResponseEntity.status(404).body("the employee with the id : " + id + " does not exist.");
        }
        Employee updateEmployee = employee.get();
        updateEmployee.setName(employeeData.getName());
        updateEmployee.setLastName(employeeData.getLastName());
        updateEmployee.setAddress(employeeData.getAddress());
        updateEmployee.setBirthDate(employeeData.getBirthDate());
        updateEmployee.setPoste(employeeData.getPoste());

        employeeRepository.save(updateEmployee);

        return ResponseEntity.status(200).body("the employee with the id :" + id + " has been updated");
    }

}