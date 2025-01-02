package com.group.autoconfienceback.services;

import com.group.autoconfienceback.dto.ApiResponse;
import com.group.autoconfienceback.dto.authentication.LoginDto;
import com.group.autoconfienceback.dto.authentication.ResetPasswordDto;
import com.group.autoconfienceback.dto.authentication.SendPasswordResetCode;
import com.group.autoconfienceback.dto.authentication.SignupDto;
import com.group.autoconfienceback.entities.Client;
import com.group.autoconfienceback.repositories.ClientRepository;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

@Service
public class AuthenticationService {

    private final AuthenticationProvider authenticationProvider;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthenticationService(AuthenticationProvider authenticationProvider, ClientRepository clientRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.authenticationProvider = authenticationProvider;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public ResponseEntity<ApiResponse<String>> login(LoginDto loginData) {
        try {
            Authentication authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword())
            );

            return ResponseEntity
                    .ok(new ApiResponse<>("Login successful"));

        } catch (Exception e) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse<>("Invalid email or password"));
        }

    }

    public ResponseEntity<ApiResponse<String>> signup(SignupDto signupData) {
        Optional<Client> client = clientRepository.findByEmail(signupData.getEmail());

        if (client.isPresent()) return ResponseEntity.status(400).body(
                new ApiResponse<>("Email already in use")
        );

        Client newClient = new Client(
                signupData.getName(),
                signupData.getLastName(),
                signupData.getAddress(),
                signupData.getEmail(),
                passwordEncoder.encode(signupData.getPassword()),
                "",
                signupData.getNumber()
        );

        clientRepository.save(newClient);

        return ResponseEntity.ok(new ApiResponse<>("Signup successful"));

    }

    public ResponseEntity<ApiResponse<String>> sendPasswordResetCode(SendPasswordResetCode data) throws MessagingException {
        Optional<Client> client = clientRepository.findByEmail(data.getEmail());

        if (client.isEmpty()) return ResponseEntity.status(400).body(
                new ApiResponse<>("No user with this email")
        );

        int code = generateRandomSixDigitNumber();

        client.get().setResetCode(String.valueOf(code));

        clientRepository.save(client.get());

        emailService.sendPasswordResetCode(data.getEmail(), client.get().getName(), String.valueOf(code));

        return ResponseEntity.ok(new ApiResponse<>("Password reset code sent successfully"));

    }

    public ResponseEntity<ApiResponse<String>> resetPassword(ResetPasswordDto resetData) {
        Optional<Client> client = clientRepository.findByEmail(resetData.getEmail());

        if (client.isEmpty()) return ResponseEntity.status(404).body(
                new ApiResponse<>("No user with this email")
        );

        if (!client.get().getResetCode().equals(resetData.getCode())) return ResponseEntity.status(403).body(
                new ApiResponse<>("Invalid reset code")
        );

        client.get().setPassword(passwordEncoder.encode(resetData.getNewPassword()));

        clientRepository.save(client.get());

        return ResponseEntity.ok(new ApiResponse<>("Password reset successful"));

    }


    private int generateRandomSixDigitNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

}
