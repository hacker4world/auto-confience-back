package com.group.autoconfienceback.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupDto {
    @NotEmpty(message = "account name cannot be empty")
    @Size(min = 2, message = "account name must be at least 2 characters long")
    private String name;
    @NotEmpty(message = "account last name cannot be empty")
    @Size(min = 2, message = "account last name must be at least 2 characters long")
    private String lastName;
    @NotEmpty(message = "account address cannot be empty")
    @Size(min = 2, message = "account address must be at least 2 characters long")
    private String address;
    @NotEmpty(message = "Account email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty(message = "account password cannot be empty")
    @Size(min = 8, message = "password must be at least 8 characters long")
    private String password;
    @NotEmpty(message = "account number cannot be empty")
    @Size(min = 8, max = 8, message = "account number must be 8 characters long")
    private String number;
}
