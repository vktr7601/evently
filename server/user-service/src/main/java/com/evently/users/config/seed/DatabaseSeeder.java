package com.evently.users.config.seed;

import com.evently.users.config.seed.models.UserPreferencesSeed;
import com.evently.users.config.seed.models.UserSeed;
import com.evently.users.user.model.User;
import com.evently.users.user.model.UserRole;
import com.evently.users.user.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.InputStream;
import java.util.List;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(UserService userServices,
                                   ObjectMapper objectMapper) {
        return args -> {
            InputStream inputStream = getClass().getResourceAsStream(
                    "/seed/user.json");

            if (inputStream == null) {
                throw new RuntimeException("Could not find /seed/user.json");
            }

            List<UserSeed> users = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<UserSeed>>() {}
            );

            userServices.seedUser(users);


            inputStream = getClass().getResourceAsStream(
                    "/seed/user-preferences.json");

            if (inputStream == null) {
                throw new RuntimeException("Could not find /seed/user.json");
            }

            List<UserPreferencesSeed> userPreferences = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<UserPreferencesSeed>>() {}
            );
            userServices.seedUserPreferences(userPreferences);
//            // 1. Create Users
//            User john = createUser("John", "Doe", 30, "john.doe@admin
//            .evently" +
//                    ".com", UserRole.ADMIN, passwordEncoder);
//            User jane = createUser("Jane", "Smith", 25, "jane.smith@email" +
//                    ".com", UserRole.USER, passwordEncoder);
//            User carlos = createUser("Carlos", "Rivera", 34, "carlos" +
//                    ".rivera@email.com", UserRole.USER, passwordEncoder);
//            User emily = createUser("Emily", "Chen", 28, "emily.chen@email" +
//                    ".com", UserRole.USER, passwordEncoder);
//            User michael = createUser("Michael", "Brown", 42, "michael" +
//                    ".brown@email.com", UserRole.USER, passwordEncoder);
//
//            userRepository.saveAll(List.of(john, jane, carlos, emily,
//            michael));

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