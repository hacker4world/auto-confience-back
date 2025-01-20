package com.group.autoconfienceback.services;

import com.group.autoconfienceback.entities.Meeting;
import com.group.autoconfienceback.entities.user_entities.Admin;
import com.group.autoconfienceback.entities.user_entities.Client;
import com.group.autoconfienceback.entities.user_entities.Employee;
import com.group.autoconfienceback.entities.user_entities.User;
import com.group.autoconfienceback.exceptions.ApiException;
import com.group.autoconfienceback.repositories.MeetingRepository;
import com.group.autoconfienceback.repositories.user_repositories.AdminRepository;
import com.group.autoconfienceback.repositories.user_repositories.ClientRepository;
import com.group.autoconfienceback.repositories.user_repositories.EmployeeRepository;
import com.group.autoconfienceback.repositories.user_repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class UtilityService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final AdminRepository adminRepository;
    private final MeetingRepository meetingRepository;

    @Autowired
    public UtilityService(ClientRepository clientRepository, UserRepository userRepository, EmployeeRepository employeeRepository, AdminRepository adminRepository, MeetingRepository meetingRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.adminRepository = adminRepository;
        this.meetingRepository = meetingRepository;
    }

    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("No client associated with this email address was found", 404));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("No account associated with this email address was found", 404));
    }

    public Employee findEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("No employee associated with this email address was found", 404));
    }

    public Admin findAdminByEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("No admin associated with this email address was found", 404));
    }

    public int generateRandomSixDigitNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    public Meeting findMeetingById(int id) {
       return meetingRepository.findById(id)
                .orElseThrow(() -> new ApiException("No meeting associated with this id", 404));
    }

    public Meeting findLatestBefore(List<Meeting> meetings, Meeting scheduledMeeting) {
        LocalDateTime scheduledTime = getScheduledTime(scheduledMeeting);
        Meeting latestBefore = null;

        for (Meeting meeting : meetings) {
            LocalDateTime meetingTime = LocalDateTime.of(meeting.getYear(), meeting.getMonth(), meeting.getDay(),
                    meeting.getHour(), meeting.getMinute());
            if (meetingTime.isBefore(scheduledTime)) {
                if (latestBefore == null || meetingTime.isAfter(
                        LocalDateTime.of(latestBefore.getYear(), latestBefore.getMonth(),
                                latestBefore.getDay(), latestBefore.getHour(), latestBefore.getMinute()))) {
                    latestBefore = meeting;
                }
            }
        }
        return latestBefore;
    }


    public Meeting findEarliestAfter(List<Meeting> meetings, Meeting scheduledMeeting) {
        LocalDateTime scheduledTime = getScheduledTime(scheduledMeeting);
        Meeting earliestAfter = null;

        for (Meeting meeting : meetings) {
            LocalDateTime meetingTime = LocalDateTime.of(meeting.getYear(), meeting.getMonth(), meeting.getDay(),
                    meeting.getHour(), meeting.getMinute());
            if (meetingTime.isAfter(scheduledTime)) {
                if (earliestAfter == null || meetingTime.isBefore(
                        LocalDateTime.of(earliestAfter.getYear(), earliestAfter.getMonth(),
                                earliestAfter.getDay(), earliestAfter.getHour(), earliestAfter.getMinute()))) {
                    earliestAfter = meeting;
                }
            }
        }
        return earliestAfter;
    }

    public Meeting findMeetingAtTheSameTime(List<Meeting> meetings, Meeting scheduledMeeting) {
        Meeting meetingAtTheSameTime = null;

        for (Meeting meeting : meetings) {
            if (meeting.getHour() == scheduledMeeting.getHour() && meeting.getMinute() == scheduledMeeting.getMinute()) {
                meetingAtTheSameTime = meeting;
            }
        }

        return meetingAtTheSameTime;
    }

    private LocalDateTime getScheduledTime(Meeting scheduledMeeting) {
        return LocalDateTime.of(
                scheduledMeeting.getYear(), scheduledMeeting.getMonth(), scheduledMeeting.getDay(),
                scheduledMeeting.getHour(), scheduledMeeting.getMinute()
        );
    }

}
