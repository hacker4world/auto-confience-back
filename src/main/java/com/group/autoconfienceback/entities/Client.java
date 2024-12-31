package com.group.autoconfienceback.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@DiscriminatorValue("client")
public class Client extends User {
    private String number;
}
