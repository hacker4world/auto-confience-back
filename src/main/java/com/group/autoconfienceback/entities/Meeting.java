package com.group.autoconfienceback.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private boolean confirmed;

    public Meeting(String carType, String carLicence, String name, String lastName, String phoneNumber, int hour, int minute, int day, int month, int year) {
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
        this.confirmed = false;
    }

    public int toMinutes() {
        return hour * 60 + minute;
    }


}
