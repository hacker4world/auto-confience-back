package com.group.autoconfienceback.mappers;

import com.group.autoconfienceback.dto.meeting_management.MeetingDto;
import com.group.autoconfienceback.dto.meeting_management.RequestMeetingDto;
import com.group.autoconfienceback.entities.Meeting;
import com.group.autoconfienceback.entities.user_entities.Client;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingMapper {
    public Meeting createMeetingFromDto(RequestMeetingDto meetingData, Client client) {
        return new Meeting(
                meetingData.getCarType(),
                meetingData.getCarLicence(),
                meetingData.getName(),
                meetingData.getLastName(),
                meetingData.getPhoneNumber(),
                meetingData.getHour(),
                meetingData.getMinute(),
                meetingData.getDay(),
                meetingData.getMonth(),
                meetingData.getYear(),
                client
        );
    }

    public List<MeetingDto> createMeetingDtosFromMeetings(List<Meeting> meetings) {
        List<MeetingDto> meetingDtos = new ArrayList<>();

        meetings.forEach(meeting -> {
            meetingDtos.add(
                    new MeetingDto(
                            meeting.getId(),
                            meeting.getCarType(),
                            meeting.getCarLicence(),
                            meeting.getName(),
                            meeting.getLastName(),
                            meeting.getPhoneNumber(),
                            meeting.getHour(),
                            meeting.getMinute(),
                            meeting.getDay(),
                            meeting.getMonth(),
                            meeting.getYear(),
                            meeting.getConfirmed()
                    )
            );
        });

        return meetingDtos;

    }

}
