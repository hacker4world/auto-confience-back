package com.group.autoconfienceback.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ResetPasswordDto {
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty(message = "Password reset code is required")
    @Size(min = 6, max = 6, message = "Password reset code is invalid")
    private String code;
    @NotEmpty(message = "New password is required")
    @Size(min = 8, max = 8, message = "New password must be at least 8 characters long")
    private String newPassword;
}
