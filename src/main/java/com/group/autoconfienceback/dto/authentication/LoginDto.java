package com.group.autoconfienceback.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LoginDto {
    @NotEmpty(message = "Account email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty(message = "Account password is required")
    private String password;
}
