package com.group.autoconfienceback.controllers;

import com.group.autoconfienceback.dto.ApiResponse;
import com.group.autoconfienceback.dto.authentication.LoginDto;
import com.group.autoconfienceback.dto.authentication.ResetPasswordDto;
import com.group.autoconfienceback.dto.authentication.SendPasswordResetCode;
import com.group.autoconfienceback.dto.authentication.SignupDto;
import com.group.autoconfienceback.services.AuthenticationService;
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

    @PostMapping("signup")
    public ResponseEntity<ApiResponse<String>> signup(@RequestBody SignupDto signupData) {
        return authenticationService.signup(signupData);
    }

    @PostMapping("send-reset-code")
    public ResponseEntity<ApiResponse<String>> sendPasswordResetCode(@RequestBody SendPasswordResetCode sendPasswordResetCode) throws MessagingException {
        return authenticationService.sendPasswordResetCode(sendPasswordResetCode);
    }

    @PostMapping("reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody ResetPasswordDto resetData) {
        return authenticationService.resetPassword(resetData);
    }

}
