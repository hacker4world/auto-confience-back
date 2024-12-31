package com.group.autoconfienceback.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@DiscriminatorValue("employee")
public class Employee extends User {

    @Lob
    private byte[] photo;

    private LocalDateTime birthDate;
    private String poste;
}
