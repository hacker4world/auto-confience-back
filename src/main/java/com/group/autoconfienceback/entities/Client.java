package com.group.autoconfienceback.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("client")
public class Client extends User {
    private String number;

    public Client(String name, String lastName, String address, String email, String password, String number) {
        super(name, lastName, address, email, password);
        this.number = number;
    }
}
