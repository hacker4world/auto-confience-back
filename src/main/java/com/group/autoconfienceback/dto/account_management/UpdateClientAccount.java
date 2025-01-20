package com.group.autoconfienceback.dto.account_management;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateClientAccount {
    @NotEmpty(message = "Account email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty(message = "Account name is required")
    private String name;
    @NotEmpty(message = "Account last name is required")
    private String lastName;
    @NotEmpty(message = "Account address is required")
    private String address;
    @NotEmpty(message = "Account number is required")
    @Size(min = 8, max = 8, message = "Account number must be at least 8 characters long")
    private String number;
}
