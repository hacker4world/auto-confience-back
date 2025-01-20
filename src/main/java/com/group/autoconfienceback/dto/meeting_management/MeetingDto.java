package com.group.autoconfienceback.dto.meeting_management;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MeetingDto {
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
}
