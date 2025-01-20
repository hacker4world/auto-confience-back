package com.group.autoconfienceback.entities.user_entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DiscriminatorValue("employee")
public class Employee extends User {

    @Lob
    private byte[] photo;

    private String birthDate;
    private String poste;

    public Employee(String name, String lastName, String address, String email, String password, String birthDate, String poste) {
        super(name, lastName, address, email, password);
        this.birthDate = birthDate;
        this.poste = poste;
    }
}
