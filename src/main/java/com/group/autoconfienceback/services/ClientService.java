package com.group.autoconfienceback.services;

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

    public ResponseEntity<String> deleteAccount(int id, String password) {
        Optional<Client> client = clientRepository.findById(id);

        if (client.isEmpty()) return ResponseEntity.status(404).body("Client with id " + id + " not found");

        String correctPassword = client.get().getPassword();

        if (!passwordEncoder.matches(password, correctPassword)) return ResponseEntity.status(401).body("Incorrect password");

        clientRepository.delete(client.get());

        return ResponseEntity.ok("Account deleted");
    }

}
