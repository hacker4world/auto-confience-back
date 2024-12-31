package com.group.autoconfienceback.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
@DiscriminatorValue("admin")
public class Admin extends User {
}
