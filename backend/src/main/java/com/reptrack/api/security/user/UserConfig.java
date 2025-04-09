package com.reptrack.api.security.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig {

    @Bean
    @Order(1)
    CommandLineRunner userLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String email = "demo@example.com";
            if (userRepository.findByEmail(email).isEmpty()) {
                User demoUser = new User();
                demoUser.setEmail(email);
                demoUser.setFirstname("Demo");
                demoUser.setLastname("User");
                demoUser.setPassword(passwordEncoder.encode("password"));
                demoUser.setRole(Role.USER);

                userRepository.save(demoUser);
            }
        };
    }
}
