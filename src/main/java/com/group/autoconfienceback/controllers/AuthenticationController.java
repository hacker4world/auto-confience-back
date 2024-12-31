package com.group.autoconfienceback.controllers;

import com.group.autoconfienceback.dto.ApiResponse;
import com.group.autoconfienceback.dto.authentication.LoginDto;
import com.group.autoconfienceback.services.AuthenticationService;
import com.group.autoconfienceback.services.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginDto loginData) {
        return authenticationService.login(loginData);

    }

}
