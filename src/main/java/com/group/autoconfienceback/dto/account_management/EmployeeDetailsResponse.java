package com.group.autoconfienceback.dto.account_management;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeDetailsResponse {
    private String name;
    private String lastName;
    private String address;
    private String email;
    private String birthDate;
    private String poste;
}
