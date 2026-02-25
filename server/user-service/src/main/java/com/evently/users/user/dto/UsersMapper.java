package com.evently.users.user.dto;

import com.evently.users.user.model.User;
import com.evently.users.user.model.UserRole;
import events.user.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UsersMapper {
    private final PasswordEncoder passwordEncoder;

    public UserRegisteredEvent toUserRegisteredEvent(User user) {
        UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent();
        userRegisteredEvent.setFirstName(user.getFirstName());
        userRegisteredEvent.setLastName(user.getLastName());
        userRegisteredEvent.setUserId(user.getId());
        if (Objects.isNull(user.getFollowCategoryList()) || user.getFollowCategoryList().isEmpty()) {
            userRegisteredEvent.setFollowCategories(new ArrayList<>());
        }
        if (Objects.isNull(user.getFollowCategoryList()) || user.getFollowCategoryList().isEmpty()) {
            userRegisteredEvent.setFollowCategories(new ArrayList<>());
        }

        userRegisteredEvent.setShouldReceiveNotification(user.isShouldReceiveNotification());
        return userRegisteredEvent;
    }

    public User toUser(RegisterUser userRequest) {
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