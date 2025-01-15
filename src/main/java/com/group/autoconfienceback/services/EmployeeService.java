package com.group.autoconfienceback.services;

import com.group.autoconfienceback.dto.ApiResponse;
import com.group.autoconfienceback.dto.UpdateEmployeeAccount;
import com.group.autoconfienceback.entities.Employee;
import com.group.autoconfienceback.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public ResponseEntity<ApiResponse<String>> updateEmployeeAccount(UpdateEmployeeAccount employeeData) {
        Optional<Employee> employee = employeeRepository.findByEmail(employeeData.getEmail());

        if (employee.isEmpty()) return ResponseEntity.badRequest().body(new ApiResponse<>("Employee not found"));

        Employee employeeToUpdate = employee.get();

        employeeToUpdate.setName(employeeData.getName());
        employeeToUpdate.setLastName(employeeData.getLastName());
        employeeToUpdate.setAddress(employeeData.getAddress());
        employeeToUpdate.setBirthDate(employeeData.getBirthDate());

        employeeRepository.save(employeeToUpdate);

        return ResponseEntity.ok(new ApiResponse<>("Employee updated successfully"));

    }

    public ResponseEntity<ApiResponse<String>> changeEmployeeImage(MultipartFile imageFile, String email) throws IOException {
        Optional<Employee> employee = employeeRepository.findByEmail(email);

        if (employee.isEmpty()) return ResponseEntity.badRequest().body(new ApiResponse<>("Employee not found"));

        Employee employeeToUpdate = employee.get();

        employeeToUpdate.setPhoto(imageFile.getBytes());

        employeeRepository.save(employeeToUpdate);

        return ResponseEntity.ok(new ApiResponse<>("Image updated successfully"));

    }


}
