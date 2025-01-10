package com.group.autoconfienceback.services;


import com.group.autoconfienceback.dto.EmployeeDto;
import com.group.autoconfienceback.entities.Employee;
import com.group.autoconfienceback.repositories.AdminRepository;
import com.group.autoconfienceback.repositories.EmployeeRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;

@Autowired
    public AdminService(AdminRepository adminRepository, EmployeeRepository employeeRepository) {
        this.adminRepository = adminRepository;
    this.employeeRepository = employeeRepository;
}

public ResponseEntity<String>CreateEmployee(EmployeeDto employeeData){
    Employee employee =new Employee(
            employeeData.getName(),
            employeeData.getLastName(),
            employeeData.getAddress(),
            employeeData.getEmail(),
            employeeData.getPassword(),
            employeeData.getResetCode(),
            employeeData.getPhoto(),
            employeeData.getBirthDate(),
            employeeData.getPoste()
    );
    employeeRepository.save(employee);
    return ResponseEntity.ok("Employee has been added successfully");


}
    public ResponseEntity<String>deleteEmployee(int id ){
    if (!adminRepository.existsById(id)){
        ResponseEntity.status(404).body("the employee with the id :"+id+" does not exist.");

    }adminRepository.deleteById(id);
        return ResponseEntity.ok("Employee deleted successfully.");}


    public ResponseEntity<String>updateEmployee(int id,EmployeeDto employeeData){
    Optional<Employee> employee= employeeRepository.findById(id);
        if(!employee.isEmpty()){
            ResponseEntity.status(404).body("the employee with the id :"+id+" does not exist.");
        }
        Employee updateEmployee =employee.get();
        updateEmployee.setName(employeeData.getName());
        updateEmployee.setLastName(employeeData.getLastName());
        updateEmployee.setAddress(employeeData.getAddress());
        updateEmployee.setEmail(employeeData.getEmail());
        updateEmployee.setPassword(employeeData.getPassword());
        updateEmployee.setResetCode(employeeData.getResetCode());
        updateEmployee.setPhoto(employeeData.getPhoto());
        updateEmployee.setBirthDate(employeeData.getBirthDate());
        updateEmployee.setPoste(employeeData.getPoste());
        employeeRepository.save(updateEmployee);
        return ResponseEntity.status(200).body("the employee with the id :"+id+" has been updated");
    }


    }

