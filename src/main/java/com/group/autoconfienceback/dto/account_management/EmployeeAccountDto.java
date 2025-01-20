package com.group.autoconfienceback.dto.account_management;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeAccountDto {
    @NotEmpty(message = "Employee name is required")
    private String name;
    @NotEmpty(message = "Employee last name is required")
    private String lastName;
    @NotEmpty(message = "Employee address is required")
    private String address;
    @NotEmpty(message = "Employee email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty(message = "Employee account password is required")
    @Size(min = 8, max = 8, message = "Password must at least be 8 characters long")
    private String password;
    @NotEmpty(message = "Employee birth date is required")
    private String birthDate;
    @NotEmpty(message = "Employee role is required")
    private String poste;
}
