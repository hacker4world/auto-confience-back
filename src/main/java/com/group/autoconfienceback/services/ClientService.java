package com.group.autoconfienceback.services;

import com.group.autoconfienceback.dto.ApiResponse;
import com.group.autoconfienceback.dto.DeleteAccountDto;
import com.group.autoconfienceback.entities.Client;
import com.group.autoconfienceback.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<ApiResponse<String>> deleteAccount(DeleteAccountDto accountData) {
        Optional<Client> client = clientRepository.findByEmail(accountData.getEmail());

        if (client.isEmpty()) return ResponseEntity.status(404).body(new ApiResponse<>("Client with given email was not found"));

        String correctPassword = client.get().getPassword();

        if (!passwordEncoder.matches(accountData.getPassword(), correctPassword)) return ResponseEntity.status(401).body(new ApiResponse<>("Incorrect password"));

        clientRepository.delete(client.get());

        return ResponseEntity.ok(new ApiResponse<>("Account deleted successfully"));
    }

}
