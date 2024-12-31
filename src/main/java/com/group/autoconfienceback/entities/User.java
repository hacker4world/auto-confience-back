package com.group.autoconfienceback.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String lastName;
    private String address;
    private String email;
    private String password;
    private String resetCode;

}
