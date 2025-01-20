package com.group.autoconfienceback.services;

import com.group.autoconfienceback.dto.*;
import com.group.autoconfienceback.dto.account_management.DeleteAccountDto;
import com.group.autoconfienceback.dto.account_management.UpdateClientAccount;
import com.group.autoconfienceback.dto.meeting_management.MeetingDto;
import com.group.autoconfienceback.dto.meeting_management.RequestMeetingDto;
import com.group.autoconfienceback.entities.user_entities.Client;
import com.group.autoconfienceback.entities.Meeting;
import com.group.autoconfienceback.exceptions.ApiException;
import com.group.autoconfienceback.mappers.MeetingMapper;
import com.group.autoconfienceback.repositories.user_repositories.ClientRepository;
import com.group.autoconfienceback.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper;
    private final UtilityService utilityService;

    @Autowired
    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder, MeetingRepository meetingRepository, MeetingMapper meetingMapper, UtilityService utilityService) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.meetingRepository = meetingRepository;
        this.meetingMapper = meetingMapper;
        this.utilityService = utilityService;
    }

    public ResponseEntity<ApiResponse<String>> deleteAccount(DeleteAccountDto accountData) {
       Client client = utilityService.findClientByEmail(accountData.getEmail());

        String correctPassword = client.getPassword();

        if (!passwordEncoder.matches(accountData.getPassword(), correctPassword))
            throw new ApiException("Incorrect account password", 403);

        clientRepository.delete(client);

        return ResponseEntity.ok(new ApiResponse<>("Account deleted successfully"));
    }

    public ResponseEntity<ApiResponse<String>> updateClientAccount(UpdateClientAccount accountData) {
        Client client = utilityService.findClientByEmail(accountData.getEmail());

        client.setName(accountData.getName());
        client.setLastName(accountData.getLastName());
        client.setAddress(accountData.getAddress());
        client.setNumber(accountData.getNumber());

        clientRepository.save(client);

        return ResponseEntity.ok(new ApiResponse<>("Account updated successfully"));
    }

    public ResponseEntity<ApiResponse<String>> requestMeeting(RequestMeetingDto meetingData) {
        Client client = utilityService.findClientByEmail(meetingData.getEmail());

        Meeting newMeeting = meetingMapper.createMeetingFromDto(meetingData, client);

        if (newMeeting.getHour() < 8 || newMeeting.getHour() > 15)
            throw new ApiException("Meeting hour must be between 8 AM and 15 PM", 409);

        if (newMeeting.getDay() < 2 || newMeeting.getDay() > 6)
            throw new ApiException("Meetings cannot be requested in saturday or sunday", 409);

        List<Meeting> meetingsOnTheSameDay = meetingRepository
                .findByYearAndMonthAndDay(meetingData.getYear(), meetingData.getMonth(), meetingData.getDay());

        Meeting meetingOnTheSameTime = utilityService.findMeetingAtTheSameTime(meetingsOnTheSameDay, newMeeting);

        if (meetingOnTheSameTime != null)
            throw new ApiException("Another meeting is scheduled at the same time and day", 409);

        else {
            Meeting meetingAfter = utilityService.findEarliestAfter(meetingsOnTheSameDay, newMeeting);
            Meeting meetingBefore = utilityService.findLatestBefore(meetingsOnTheSameDay, newMeeting);

            if (meetingBefore == null && meetingAfter == null) {
                meetingRepository.save(newMeeting);
                return ResponseEntity.ok(new ApiResponse<>("Meeting added successfully"));
            }

            else if (meetingBefore != null && meetingAfter != null) {
                if (meetingBefore.toMinutes() + 90 <= newMeeting.toMinutes() && newMeeting.toMinutes() + 90 <= meetingAfter.toMinutes()) {
                    meetingRepository.save(newMeeting);
                    return ResponseEntity.ok(new ApiResponse<>("Meeting added successfully"));
                } else throw new ApiException("The scheduled meeting time conflicts with another meeting", 409);
            }

            else if (meetingBefore == null) {
                if (newMeeting.toMinutes() + 90 <= meetingAfter.toMinutes()) {
                    meetingRepository.save(newMeeting);
                    return ResponseEntity.ok(new ApiResponse<>("Meeting added successfully"));
                } else {
                    throw new ApiException("The scheduled meeting time conflicts with another meeting", 409);
                }
            }

            else {
                if (meetingBefore.toMinutes() + 90 <= newMeeting.toMinutes()) {
                    meetingRepository.save(newMeeting);
                    return ResponseEntity.ok(new ApiResponse<>("Meeting added successfully"));
                }
                else {
                    throw new ApiException("The scheduled meeting time conflicts with another meeting", 409);
                }
            }

        }

    }

    public ResponseEntity<ApiResponse<List<MeetingDto>>> getSubmittedMeetings(String email) {
        Client client = utilityService.findClientByEmail(email);

        List<Meeting> meetings = meetingRepository.findByClient(client);

        List<MeetingDto> meetingList = meetingMapper.createMeetingDtosFromMeetings(meetings);

        return ResponseEntity.ok(new ApiResponse<>("meetings list", meetingList));
    }

}
