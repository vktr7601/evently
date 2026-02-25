package com.evently.users.user.controller;

import com.evently.users.user.dto.RegisterUser;
import com.evently.users.user.dto.UserDetailsDto;
import com.evently.users.user.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Map<String, String>> createUser( @RequestBody RegisterUser userRequest) {
        userService.createUser(userRequest);

        //if sucessfull we need to generate jwt token
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message"
                , "User registered successfully"));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDetailsDto> userDetails(@RequestHeader("X-User" +
            "-Id") Long userId) {
        if(userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(null);
    }
}