package com.evently.users.user.controller;

import com.evently.users.user.dto.UserDetails;
import com.evently.users.user.dto.UserPreferences;
import com.evently.users.user.dto.request.RegisterUser;
import com.evently.users.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody RegisterUser userRequest) {
        userService.createUser(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message"
                , "User registered successfully"));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDetails> userDetails(@RequestHeader("X-User" +
            "-Id") Long userId) {

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        UserDetails userDetails = userService.getUserDetails(userId);
        return ResponseEntity.ok(userDetails);
    }

    @GetMapping("/preferences")
    public ResponseEntity<UserPreferences> getUserPreferences(@RequestHeader(
            "X-User-Id") Long userId) {
        UserPreferences userPreferences =
                userService.getUserPreferences(userId);

        return ResponseEntity.ok(userPreferences);
    }
}