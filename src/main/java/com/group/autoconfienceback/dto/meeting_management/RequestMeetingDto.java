package com.group.autoconfienceback.dto.meeting_management;

import lombok.Getter;

@Getter
public class RequestMeetingDto {
    private String email;
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
}
