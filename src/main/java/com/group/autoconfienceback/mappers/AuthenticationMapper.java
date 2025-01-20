package com.group.autoconfienceback.mappers;

import com.group.autoconfienceback.dto.authentication.ProfileDetailsResponse;
import com.group.autoconfienceback.dto.authentication.SignupDto;
import com.group.autoconfienceback.entities.user_entities.Admin;
import com.group.autoconfienceback.entities.user_entities.Client;
import com.group.autoconfienceback.entities.user_entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public Client createClientFromDto(SignupDto signupData) {
        return new Client(
                signupData.getName(),
                signupData.getLastName(),
                signupData.getAddress(),
                signupData.getEmail(),
                passwordEncoder.encode(signupData.getPassword()),
                signupData.getNumber()
        );
    }

    public ProfileDetailsResponse createEmployeeProfileDetails(Employee employee) {
        return new ProfileDetailsResponse(employee.getName(), employee.getLastName(), employee.getAddress(), employee.getBirthDate(), employee.getPoste());
    }

    public ProfileDetailsResponse createClientProfileDetails(Client client) {
        return new ProfileDetailsResponse(client.getName(), client.getLastName(), client.getAddress(), client.getNumber());
    }

    public ProfileDetailsResponse createAdminProfileDetails(Admin admin) {
        return new ProfileDetailsResponse(admin.getName(), admin.getLastName(), admin.getAddress());
    }
}
