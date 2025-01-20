package com.group.autoconfienceback.entities.user_entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@Getter
@Setter
@DiscriminatorValue("admin")
public class Admin extends User {
    public Admin(String name, String lastName, String address, String email, String password) {
        super(name, lastName, address, email, password);
    }
}
