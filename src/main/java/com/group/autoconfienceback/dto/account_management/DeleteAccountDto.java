package com.group.autoconfienceback.dto.account_management;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class DeleteAccountDto {
    @NotEmpty(message = "Account email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty(message = "Account password is required")
    @Size(min = 8, max = 8, message = "Account password must at least have 8 characters long")
    private String password;
}
