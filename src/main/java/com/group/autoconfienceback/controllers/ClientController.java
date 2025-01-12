package com.group.autoconfienceback.controllers;

import com.group.autoconfienceback.dto.DeleteAccountDto;
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

    @DeleteMapping("delete-account/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") int id, @RequestBody DeleteAccountDto accountDto) {
        return clientService.deleteAccount(id, accountDto.getPassword());
    }

}
