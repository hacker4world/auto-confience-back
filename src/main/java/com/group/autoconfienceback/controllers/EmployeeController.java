package com.group.autoconfienceback.controllers;

import com.group.autoconfienceback.dto.meeting_management.AcceptOrRefuseMeetingDto;
import com.group.autoconfienceback.dto.ApiResponse;
import com.group.autoconfienceback.dto.meeting_management.MeetingDto;
import com.group.autoconfienceback.dto.account_management.UpdateEmployeeAccount;
import com.group.autoconfienceback.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PutMapping("update-account")
    public ResponseEntity<ApiResponse<String>> updateEmployeeAccount(@Valid @RequestBody UpdateEmployeeAccount employeeData) {
        return employeeService.updateEmployeeAccount(employeeData);
    }

    @PutMapping("change-image")
    public ResponseEntity<ApiResponse<String>> changeEmployeeImage(@Valid @RequestParam("image") MultipartFile imageFile, @RequestParam("email") String email) throws IOException {
        return employeeService.changeEmployeeImage(imageFile, email);
    }

    @PostMapping("handle-meeting")
    public ResponseEntity<ApiResponse<String>> handleMeeting(@Valid @RequestBody AcceptOrRefuseMeetingDto meeting) {
        return employeeService.acceptOrRefuseMeeting(meeting);
    }

    @GetMapping("pending-meetings")
    public ResponseEntity<ApiResponse<List<MeetingDto>>> getPendingMeetings() {
        return employeeService.getPendingMeetings();
    }

    @GetMapping("accepted-meetings")
    public ResponseEntity<ApiResponse<List<MeetingDto>>> getAcceptedMeetings() {
        return employeeService.getAcceptedMeetings();
    }



}
