package com.evently.users.config;

import com.evently.users.user.model.User;
import com.evently.users.user.model.UserRole;
import com.evently.users.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() > 0) return; // Prevent duplicate seeding

            // 1. Create Users
            User john = createUser("John", "Doe", 30, "john.doe@admin.evently" +
                    ".com", UserRole.ADMIN, passwordEncoder);
            User jane = createUser("Jane", "Smith", 25, "jane.smith@email" +
                    ".com", UserRole.USER, passwordEncoder);
            User carlos = createUser("Carlos", "Rivera", 34, "carlos" +
                    ".rivera@email.com",  UserRole.USER, passwordEncoder);
            User emily = createUser("Emily", "Chen", 28, "emily.chen@email" +
                    ".com",  UserRole.USER, passwordEncoder);
            User michael = createUser("Michael", "Brown", 42, "michael" +
                    ".brown@email.com",  UserRole.USER, passwordEncoder);

            userRepository.saveAll(List.of(john, jane, carlos, emily, michael));

        };


    }

    private User createUser(String first, String last, int age, String email,
                            UserRole role, PasswordEncoder encoder) {
        User user = new User();
        user.setFirstName(first);
        user.setLastName(last);
        user.setAge(age);
        user.setEmail(email);
        user.setUserRole(role);
        user.setShouldReceiveNotification(true);
        user.setPassword(encoder.encode("password123"));
        return user;
    }
}