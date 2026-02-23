package com.evently.users.user.entities;

import com.evently.users.user.User;
import dtos.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsersMapper {
    private final PasswordEncoder passwordEncoder;

    public UserRegisteredEvent toUserRegisteredEvent(User user) {
        UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent();
        userRegisteredEvent.setFirstName(user.getFirstName());
        userRegisteredEvent.setLastName(user.getLastName());
        userRegisteredEvent.setUserId(user.getId());
        userRegisteredEvent.setUuid(UUID.randomUUID());
        return userRegisteredEvent;
    }

    public User toUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setAge(userRequest.getAge());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setShouldReceiveNotification(userRequest.isSubscribedToNewsletter());
        if (userRequest.getEmail().contains("@admin.evently.com")) {
            user.setUserRole(UserRole.ADMIN);
        } else {
            user.setUserRole(UserRole.USER);
        }

        return user;
    }
}