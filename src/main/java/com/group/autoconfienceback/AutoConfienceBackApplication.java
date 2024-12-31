package com.group.autoconfienceback;

import com.group.autoconfienceback.entities.Client;
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

}
