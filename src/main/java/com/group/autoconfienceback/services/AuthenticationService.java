package com.group.autoconfienceback.services;

import com.group.autoconfienceback.dto.ApiResponse;
import com.group.autoconfienceback.dto.authentication.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationProvider authenticationProvider;

    public AuthenticationService(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
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

}
