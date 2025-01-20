package com.group.autoconfienceback.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SendPasswordResetCodeDto {
    @NotEmpty(message = "Account email is required")
    @Email(message = "Invalid email format")
    private String email;
}
