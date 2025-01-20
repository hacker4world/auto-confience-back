package com.group.autoconfienceback.services;

import com.group.autoconfienceback.dto.meeting_management.AcceptOrRefuseMeetingDto;
import com.group.autoconfienceback.dto.ApiResponse;
import com.group.autoconfienceback.dto.meeting_management.MeetingDto;
import com.group.autoconfienceback.dto.account_management.UpdateEmployeeAccount;
import com.group.autoconfienceback.entities.user_entities.Employee;
import com.group.autoconfienceback.entities.Meeting;
import com.group.autoconfienceback.mappers.MeetingMapper;
import com.group.autoconfienceback.repositories.user_repositories.EmployeeRepository;
import com.group.autoconfienceback.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper;
    private final UtilityService utilityService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, MeetingRepository meetingRepository, MeetingMapper meetingMapper, UtilityService utilityService) {
        this.employeeRepository = employeeRepository;
        this.meetingRepository = meetingRepository;
        this.meetingMapper = meetingMapper;
        this.utilityService = utilityService;
    }

    public ResponseEntity<ApiResponse<String>> updateEmployeeAccount(UpdateEmployeeAccount employeeData) {
        Employee employee = utilityService.findEmployeeByEmail(employeeData.getEmail());

        employee.setName(employeeData.getName());
        employee.setLastName(employeeData.getLastName());
        employee.setAddress(employeeData.getAddress());
        employee.setBirthDate(employeeData.getBirthDate());

        employeeRepository.save(employee);

        return ResponseEntity.ok(new ApiResponse<>("Employee updated successfully"));

    }

    public ResponseEntity<ApiResponse<String>> changeEmployeeImage(MultipartFile imageFile, String email) throws IOException {
        Employee employee = utilityService.findEmployeeByEmail(email);

        employee.setPhoto(imageFile.getBytes());

        employeeRepository.save(employee);

        return ResponseEntity.ok(new ApiResponse<>("Image updated successfully"));

    }

    public ResponseEntity<ApiResponse<String>> acceptOrRefuseMeeting(AcceptOrRefuseMeetingDto data) {
        Meeting meeting = utilityService.findMeetingById(data.getMeetingId());

        int confirmed;
        if (data.isAccepted()) confirmed = 1;
        else confirmed = -1;

        if (meeting.getConfirmed() == confirmed) return ResponseEntity.badRequest().body(new ApiResponse<>("Requested action already applied to the meeting"));

        meeting.setConfirmed(confirmed);

        meetingRepository.save(meeting);

        return ResponseEntity.ok(new ApiResponse<>("Request action has been saved"));

    }

    public ResponseEntity<ApiResponse<List<MeetingDto>>> getPendingMeetings() {
        List<Meeting> pendingMeetings = meetingRepository.findByConfirmed(0);

        List<MeetingDto> meetingList = meetingMapper.createMeetingDtosFromMeetings(pendingMeetings);

        return ResponseEntity.ok(new ApiResponse<>("pending meetings list", meetingList));
    }

    public ResponseEntity<ApiResponse<List<MeetingDto>>> getAcceptedMeetings() {
        List<Meeting> acceptedMeetings = meetingRepository.findByConfirmed(1);

        List<MeetingDto> meetingList = meetingMapper.createMeetingDtosFromMeetings(acceptedMeetings);

        return ResponseEntity.ok(new ApiResponse<>("accepted meetings list", meetingList));
    }


}
