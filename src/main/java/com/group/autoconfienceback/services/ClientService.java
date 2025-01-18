package com.group.autoconfienceback.services;

import com.group.autoconfienceback.dto.ApiResponse;
import com.group.autoconfienceback.dto.DeleteAccountDto;
import com.group.autoconfienceback.dto.RequestMeetingDto;
import com.group.autoconfienceback.dto.UpdateClientAccount;
import com.group.autoconfienceback.entities.Client;
import com.group.autoconfienceback.entities.Meeting;
import com.group.autoconfienceback.repositories.ClientRepository;
import com.group.autoconfienceback.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final MeetingRepository meetingRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder, MeetingRepository meetingRepository) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.meetingRepository = meetingRepository;
    }

    public ResponseEntity<ApiResponse<String>> deleteAccount(DeleteAccountDto accountData) {
        Optional<Client> client = clientRepository.findByEmail(accountData.getEmail());

        if (client.isEmpty()) return ResponseEntity.status(404).body(new ApiResponse<>("Client with given email was not found"));

        String correctPassword = client.get().getPassword();

        if (!passwordEncoder.matches(accountData.getPassword(), correctPassword)) return ResponseEntity.status(401).body(new ApiResponse<>("Incorrect password"));

        clientRepository.delete(client.get());

        return ResponseEntity.ok(new ApiResponse<>("Account deleted successfully"));
    }

    public ResponseEntity<ApiResponse<String>> updateClientAccount(UpdateClientAccount accountData) {
        Optional<Client> client = clientRepository.findByEmail(accountData.getEmail());

        if (client.isEmpty()) return ResponseEntity.status(404).body(new ApiResponse<>("Client with given email was not found"));

        Client clientToUpdate = client.get();

        clientToUpdate.setName(accountData.getName());
        clientToUpdate.setLastName(accountData.getLastName());
        clientToUpdate.setAddress(accountData.getAddress());
        clientToUpdate.setNumber(accountData.getNumber());

        clientRepository.save(clientToUpdate);

        return ResponseEntity.ok(new ApiResponse<>("Account updated successfully"));
    }

    public ResponseEntity<ApiResponse<String>> requestMeeting(RequestMeetingDto meetingData) {

        Optional<Client> client = clientRepository.findByEmail(meetingData.getEmail());

        if (client.isEmpty()) return ResponseEntity.status(404).body(new ApiResponse<>("Client with given email was not found"));

        Meeting newMeeting = new Meeting(
                meetingData.getCarType(),
                meetingData.getCarLicence(),
                meetingData.getName(),
                meetingData.getLastName(),
                meetingData.getPhoneNumber(),
                meetingData.getHour(),
                meetingData.getMinute(),
                meetingData.getDay(),
                meetingData.getMonth(),
                meetingData.getYear()
        );

        // check if there is no meeting in that date
        List<Meeting> meetingsOnTheSameDay = meetingRepository.findByYearAndMonthAndDay(meetingData.getYear(), meetingData.getMonth(), meetingData.getDay());

        if (meetingsOnTheSameDay.isEmpty()) {
            meetingRepository.save(newMeeting);
            return ResponseEntity.ok(new ApiResponse<>("Meeting added successfully"));
        }
        else {
            Meeting earliestMeetingAfter = findEarliestAfter(meetingsOnTheSameDay, newMeeting);
            Meeting latestMeetingBefore = findLatestBefore(meetingsOnTheSameDay, newMeeting);

            if (latestMeetingBefore == null && earliestMeetingAfter != null) {
                if (newMeeting.toMinutes() + 90 <= earliestMeetingAfter.toMinutes()) {
                    meetingRepository.save(newMeeting);
                    return ResponseEntity.ok(new ApiResponse<>("Meeting added successfully"));
                } else {
                    return ResponseEntity.status(400).body(new ApiResponse<>("Meeting cannot be taken at this time"));
                }
            }

            if (earliestMeetingAfter == null && latestMeetingBefore != null) {
                if (latestMeetingBefore.toMinutes() + 90 <= newMeeting.toMinutes()) {
                    meetingRepository.save(newMeeting);
                    return ResponseEntity.ok(new ApiResponse<>("Meeting added successfully"));
                }
                else {
                    return ResponseEntity.status(400).body(new ApiResponse<>("Meeting cannot be taken at this time"));
                }
            }

            if (latestMeetingBefore.toMinutes() + 90 <= newMeeting.toMinutes() && newMeeting.toMinutes() + 90 <= earliestMeetingAfter.toMinutes()) {
                meetingRepository.save(newMeeting);
                return ResponseEntity.ok(new ApiResponse<>("Meeting added successfully"));
            } else {
                return ResponseEntity.status(400).body(new ApiResponse<>("Meeting cannot be taken at this time"));
            }

        }

    }

    private Meeting findLatestBefore(List<Meeting> meetings, Meeting scheduledMeeting) {
        LocalDateTime scheduledTime = LocalDateTime.of(
                scheduledMeeting.getYear(), scheduledMeeting.getMonth(), scheduledMeeting.getDay(),
                scheduledMeeting.getHour(), scheduledMeeting.getMinute()
        );
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


    private Meeting findEarliestAfter(List<Meeting> meetings, Meeting scheduledMeeting) {
        LocalDateTime scheduledTime = LocalDateTime.of(
                scheduledMeeting.getYear(), scheduledMeeting.getMonth(), scheduledMeeting.getDay(),
                scheduledMeeting.getHour(), scheduledMeeting.getMinute()
        );
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

}
