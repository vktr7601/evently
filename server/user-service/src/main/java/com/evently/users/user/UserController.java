package com.evently.users.user;

import com.evently.users.user.entities.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/internal/getUsers")
    public Set<Long> getUsersByPrefences(@RequestBody List<Long> preferences) {
        var userIds = userService.getUserPreferences(preferences);
        return userIds;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody UserRequest user) {
        userService.createUser(user);
        return null;
    }
}