package com.group.autoconfienceback.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmployeeAccount {
    private String email;
    private String name;
    private String lastName;
    private String address;
    private String birthDate;
}
