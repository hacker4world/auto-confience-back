package com.group.autoconfienceback.mappers;

import com.group.autoconfienceback.dto.account_management.EmployeeDetailsResponse;
import com.group.autoconfienceback.dto.account_management.EmployeeAccountDto;
import com.group.autoconfienceback.entities.user_entities.Employee;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountManagementMapper {

    private PasswordEncoder passwordEncoder;

    public Employee createEmployeeFromDto(EmployeeAccountDto employeeData) {
        return new Employee(
                employeeData.getName(),
                employeeData.getLastName(),
                employeeData.getAddress(),
                employeeData.getEmail(),
                passwordEncoder.encode(employeeData.getPassword()),
                employeeData.getBirthDate(),
                employeeData.getPoste()
        );
    }

    public List<EmployeeDetailsResponse> createEmployeeListFromEmployees(List<Employee> employees) {
        List<EmployeeDetailsResponse> employeesDto = new ArrayList<>();

        employees.forEach(employee -> {
            EmployeeDetailsResponse details = new EmployeeDetailsResponse(employee.getName(), employee.getLastName(), employee.getAddress(), employee.getEmail(), employee.getBirthDate(), employee.getPoste());
            employeesDto.add(details);
        });

        return employeesDto;
    }

}
