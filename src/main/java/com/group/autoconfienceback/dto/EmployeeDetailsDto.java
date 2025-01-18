package com.group.autoconfienceback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeDetailsDto {
    private String name;
    private String lastName;
    private String address;
    private String email;
    private String birthDate;
    private String poste;
}
