package com.group.autoconfienceback.controllers;

import com.group.autoconfienceback.dto.ApiResponse;
import com.group.autoconfienceback.dto.DeleteAccountDto;
import com.group.autoconfienceback.dto.RequestMeetingDto;
import com.group.autoconfienceback.dto.UpdateClientAccount;
import com.group.autoconfienceback.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @DeleteMapping("delete-account")
    public ResponseEntity<ApiResponse<String>> deleteAccount(@RequestBody DeleteAccountDto accountDto) {
        return clientService.deleteAccount(accountDto);
    }

    @PutMapping("update-account")
    public ResponseEntity<ApiResponse<String>> updateAccount(@RequestBody UpdateClientAccount accountDto) {
        return clientService.updateClientAccount(accountDto);
    }

    @PostMapping("request-meeting")
    public ResponseEntity<ApiResponse<String>> requestMeeting(@RequestBody RequestMeetingDto requestMeetingDto) {
        return clientService.requestMeeting(requestMeetingDto);
    }

}
