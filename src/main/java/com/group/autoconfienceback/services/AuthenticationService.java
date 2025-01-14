package com.group.autoconfienceback.services;

import com.group.autoconfienceback.dto.ApiResponse;
import com.group.autoconfienceback.dto.UpdateAccountPassword;
import com.group.autoconfienceback.dto.authentication.*;
import com.group.autoconfienceback.entities.Client;
import com.group.autoconfienceback.entities.Employee;
import com.group.autoconfienceback.entities.User;
import com.group.autoconfienceback.repositories.ClientRepository;
import com.group.autoconfienceback.repositories.UserRepository;
import com.group.autoconfienceback.security.JwtService;
import jakarta.mail.MessagingException;
import org.apache.coyote.Response;
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
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthenticationService(AuthenticationProvider authenticationProvider, ClientRepository clientRepository, PasswordEncoder passwordEncoder, EmailService emailService, JwtService jwtService, UserRepository userRepository) {
        this.authenticationProvider = authenticationProvider;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public ResponseEntity<ApiResponse<LoginResponeDto>> login(LoginDto loginData) {
        try {
            Authentication authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword())
            );

            User user = userRepository.findByEmail(loginData.getEmail()).get();

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

        if (client.isPresent()) return ResponseEntity.status(400).body(
                new ApiResponse<>("Email already in use")
        );

        Client newClient = new Client(
                signupData.getName(),
                signupData.getLastName(),
                signupData.getAddress(),
                signupData.getEmail(),
                passwordEncoder.encode(signupData.getPassword()),
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

    public ResponseEntity<ApiResponse<String>> updateAccountPassword(UpdateAccountPassword accountData) {
        Optional<User> user = userRepository.findByEmail(accountData.getEmail());

        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse<>("the user with the email : " + accountData.getEmail() + " does not exist."));
        }

        User updateUser = user.get();

        if (!passwordEncoder.matches(accountData.getOldPassword(), updateUser.getPassword())) return ResponseEntity.status(403).body(new ApiResponse<>("Invalid old password"));

        updateUser.setPassword(passwordEncoder.encode(accountData.getNewPassword()));

        userRepository.save(updateUser);

        return ResponseEntity.status(200).body(new ApiResponse<>("Password updated successfully"));

    }

    public ResponseEntity<ApiResponse<ProfileDetails>> getProfileDetails(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse<>("the user with the email : " + email + " does not exist."));
        }

        User userInformations = user.get();

        ProfileDetails details;

        if (userInformations instanceof Employee employee) {
            details = new ProfileDetails(employee.getName(), employee.getLastName(), employee.getAddress(), employee.getBirthDate(), employee.getPoste());
        } else if (userInformations instanceof Client client) {
            details = new ProfileDetails(client.getName(), client.getLastName(), client.getAddress(), client.getNumber());
        } else {
            details = new ProfileDetails(userInformations.getName(), userInformations.getLastName(), userInformations.getAddress());
        }

        return ResponseEntity.ok(new ApiResponse<>("Profile details retrieved successfully", details));

    }


    private int generateRandomSixDigitNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

}
