package com.group.autoconfienceback.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccountPassword {
    private String email;
    private String oldPassword;
    private String newPassword;
}
