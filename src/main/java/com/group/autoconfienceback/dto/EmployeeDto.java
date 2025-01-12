package com.group.autoconfienceback.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class EmployeeDto {
    private String name;
    private String lastName;
    private String address;
    private String email;
    private String password;
    private String birthDate;
    private String poste;
}
