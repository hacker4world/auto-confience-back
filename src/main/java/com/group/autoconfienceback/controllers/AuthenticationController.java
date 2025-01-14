package com.group.autoconfienceback.controllers;

import com.group.autoconfienceback.dto.ApiResponse;
import com.group.autoconfienceback.dto.UpdateAccountPassword;
import com.group.autoconfienceback.dto.authentication.*;
import com.group.autoconfienceback.services.AuthenticationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<LoginResponeDto>> login(@RequestBody LoginDto loginData) {
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

    @PutMapping("update-password")
    public ResponseEntity<ApiResponse<String>> updatePassword(@RequestBody UpdateAccountPassword updateAccountPassword) {
        return authenticationService.updateAccountPassword(updateAccountPassword);
    }

    @GetMapping("profile/{email}")
    public ResponseEntity<ApiResponse<ProfileDetails>> getProfileDetails(@PathVariable String email) {
        return authenticationService.getProfileDetails(email);
    }

}
