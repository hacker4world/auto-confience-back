package com.group.autoconfienceback.security;

import com.group.autoconfienceback.entities.Admin;
import com.group.autoconfienceback.entities.Client;
import com.group.autoconfienceback.entities.Employee;
import com.group.autoconfienceback.entities.User;
import com.group.autoconfienceback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        String userRole = "";

        if (user instanceof Client) userRole = "ROLE_CLIENT";
        else if (user instanceof Employee) userRole = "ROLE_EMPLOYEE";
        else if (user instanceof Admin) userRole = "ROLE_ADMIN";

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(userRole))
        );

    }
}
