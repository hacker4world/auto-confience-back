package com.group.autoconfienceback.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DiscriminatorValue("employee")
public class Employee extends User {

    @Lob
    private byte[] photo;

    private LocalDateTime birthDate;
    private String poste;

    public Employee(String name, String lastName, String address, String email, String password, String resetCode, byte[] photo, LocalDateTime birthDate, String poste) {
        super(name, lastName, address, email, password, resetCode);
        this.photo = photo;
        this.birthDate = birthDate;
        this.poste = poste;
    }
}
