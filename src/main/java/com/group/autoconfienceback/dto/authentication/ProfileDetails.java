package com.group.autoconfienceback.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProfileDetails {

    private String name;
    private String lastName;
    private String address;
    private String birthDate;
    private String poste;
    private String number;

    public ProfileDetails(String name, String lastName, String address, String birthDate, String poste) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.birthDate = birthDate;
        this.poste = poste;
    }

    public ProfileDetails(String name, String lastName, String address, String number) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.number = number;
    }

    public ProfileDetails(String name, String lastName, String address) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.number = number;
    }

}
