package com.group.autoconfienceback.controllers;

import com.group.autoconfienceback.dto.*;
import com.group.autoconfienceback.dto.account_management.DeleteAccountDto;
import com.group.autoconfienceback.dto.account_management.UpdateClientAccount;
import com.group.autoconfienceback.dto.meeting_management.MeetingDto;
import com.group.autoconfienceback.dto.meeting_management.RequestMeetingDto;
import com.group.autoconfienceback.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @DeleteMapping("delete-account")
    public ResponseEntity<ApiResponse<String>> deleteAccount(@Valid @RequestBody DeleteAccountDto accountDto) {
        return clientService.deleteAccount(accountDto);
    }

    @PutMapping("update-account")
    public ResponseEntity<ApiResponse<String>> updateAccount(@Valid @RequestBody UpdateClientAccount accountDto) {
        return clientService.updateClientAccount(accountDto);
    }

    @PostMapping("request-meeting")
    public ResponseEntity<ApiResponse<String>> requestMeeting(@Valid @RequestBody RequestMeetingDto requestMeetingDto) {
        return clientService.requestMeeting(requestMeetingDto);
    }

    @GetMapping("submitted-meetings/{email}")
    public ResponseEntity<ApiResponse<List<MeetingDto>>> submittedMeetings(@PathVariable("email") String email) {
        return clientService.getSubmittedMeetings(email);
    }

}
