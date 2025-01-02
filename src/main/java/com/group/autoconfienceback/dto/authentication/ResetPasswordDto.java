package com.group.autoconfienceback.dto.authentication;

import lombok.Getter;

@Getter
public class ResetPasswordDto {
    private String email;
    private String code;
    private String newPassword;
}
