package com.group.autoconfienceback.entities;

import com.group.autoconfienceback.entities.user_entities.Client;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String carType;
    private String carLicence;
    private String name;
    private String lastName;
    private String phoneNumber;
    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;
    private int confirmed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public Meeting(String carType, String carLicence, String name, String lastName, String phoneNumber, int hour, int minute, int day, int month, int year, Client client) {
        this.carType = carType;
        this.carLicence = carLicence;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
        this.confirmed = 0;
        this.client = client;
    }

    public int toMinutes() {
        return hour * 60 + minute;
    }


}
