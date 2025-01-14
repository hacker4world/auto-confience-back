package com.group.autoconfienceback.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponeDto {
    private String role;
    private String token;
    private String email;
}
