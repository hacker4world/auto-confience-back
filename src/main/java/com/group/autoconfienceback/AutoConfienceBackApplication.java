package com.group.autoconfienceback;

import com.group.autoconfienceback.entities.Admin;
import com.group.autoconfienceback.entities.Client;
import com.group.autoconfienceback.repositories.AdminRepository;
import com.group.autoconfienceback.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AutoConfienceBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoConfienceBackApplication.class, args);
    }

//    @Bean
    public CommandLineRunner run(ClientRepository clientRepository, PasswordEncoder passwordEncoder, AdminRepository adminRepository) {
        return args -> {

            String password = passwordEncoder.encode("12345678");

            Admin admin = new Admin("hamdi", "jbeli", "mourouj", "hamdi@gmail.com", password);

            adminRepository.save(admin);
        };

    }
}