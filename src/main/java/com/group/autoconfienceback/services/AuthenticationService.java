package com.group.autoconfienceback.services;

import com.group.autoconfienceback.dto.ApiResponse;
import com.group.autoconfienceback.dto.account_management.UpdateAccountPassword;
import com.group.autoconfienceback.dto.authentication.*;
import com.group.autoconfienceback.entities.user_entities.Admin;
import com.group.autoconfienceback.entities.user_entities.Client;
import com.group.autoconfienceback.entities.user_entities.Employee;
import com.group.autoconfienceback.entities.user_entities.User;
import com.group.autoconfienceback.exceptions.ApiException;
import com.group.autoconfienceback.mappers.AuthenticationMapper;
import com.group.autoconfienceback.repositories.user_repositories.ClientRepository;
import com.group.autoconfienceback.repositories.user_repositories.UserRepository;
import com.group.autoconfienceback.security.JwtService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final AuthenticationProvider authenticationProvider;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationMapper authenticationMapper;
    private final UtilityService utilityService;

    public AuthenticationService(AuthenticationProvider authenticationProvider, ClientRepository clientRepository, PasswordEncoder passwordEncoder, EmailService emailService, JwtService jwtService, UserRepository userRepository, AuthenticationMapper authenticationMapper, UtilityService utilityService) {
        this.authenticationProvider = authenticationProvider;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationMapper = authenticationMapper;
        this.utilityService = utilityService;
    }

    public ResponseEntity<ApiResponse<LoginResponeDto>> login(LoginDto loginData) {
        try {
            authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword())
            );

            User user = utilityService.findUserByEmail(loginData.getEmail());

            String token = jwtService.generateToken(loginData.getEmail());

            String userRole = "";

            if (user instanceof Client) userRole = "client";
            else if (user instanceof Employee) userRole = "employee";
            else userRole = "admin";

            return ResponseEntity.ok(new ApiResponse<>("Login successful", new LoginResponeDto(userRole, token, loginData.getEmail())));

        } catch (Exception e) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse<>("Invalid email or password"));
        }

    }

    public ResponseEntity<ApiResponse<String>> signup(SignupDto signupData) {
        Optional<Client> client = clientRepository.findByEmail(signupData.getEmail());

        if (client.isPresent()) throw new ApiException("Email is already in use", 409);

        Client newClient = authenticationMapper.createClientFromDto(signupData);

        clientRepository.save(newClient);

        return ResponseEntity.ok(new ApiResponse<>("Signup successful"));

    }

    public ResponseEntity<ApiResponse<String>> sendPasswordResetCode(SendPasswordResetCodeDto data) throws MessagingException {
        Client client = utilityService.findClientByEmail(data.getEmail());

        int code = utilityService.generateRandomSixDigitNumber();

        client.setResetCode(String.valueOf(code));

        clientRepository.save(client);

        emailService.sendPasswordResetCode(data.getEmail(), client.getName(), String.valueOf(code));

        return ResponseEntity.ok(new ApiResponse<>("Password reset code sent successfully"));

    }

    public ResponseEntity<ApiResponse<String>> resetPassword(ResetPasswordDto resetData) {
        Client client = utilityService.findClientByEmail(resetData.getEmail());

        if (!client.getResetCode().equals(resetData.getCode())) return ResponseEntity.status(403).body(
                new ApiResponse<>("Invalid reset code")
        );

        client.setPassword(passwordEncoder.encode(resetData.getNewPassword()));

        clientRepository.save(client);

        return ResponseEntity.ok(new ApiResponse<>("Password reset successful"));

    }

    public ResponseEntity<ApiResponse<String>> updateAccountPassword(UpdateAccountPassword accountData) {
        User user = utilityService.findUserByEmail(accountData.getEmail());

        if (!passwordEncoder.matches(accountData.getOldPassword(), user.getPassword())) return ResponseEntity.status(403).body(new ApiResponse<>("Invalid old password"));

        user.setPassword(passwordEncoder.encode(accountData.getNewPassword()));

        userRepository.save(user);

        return ResponseEntity.status(200).body(new ApiResponse<>("Password updated successfully"));

    }

    public ResponseEntity<ApiResponse<ProfileDetailsResponse>> getProfileDetails(String email) {
        User user = utilityService.findUserByEmail(email);

        ProfileDetailsResponse details;

        if (user instanceof Employee employee) {
            details = authenticationMapper.createEmployeeProfileDetails(employee);
        } else if (user instanceof Client client) {
            details = authenticationMapper.createClientProfileDetails(client);
        } else {
            details = authenticationMapper.createAdminProfileDetails((Admin) user);
        }

        return ResponseEntity.ok(new ApiResponse<>("Profile details retrieved successfully", details));

    }

}
