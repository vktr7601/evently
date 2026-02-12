package com.evently.users.user;

import com.evently.users.user.auth.AuthService;
import com.evently.users.user.entities.LoginRequest;
import com.evently.users.user.entities.UserRequest;
import jwt.JWTResponse;
import jwt.JWTUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtility jwtUtility;
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody UserRequest userRequest) {

        userService.createUser(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody LoginRequest loginRequest) {
        String autToken = authService.authenticateAndGenerateToken(loginRequest.getEmail(), loginRequest.getPassword());

        return ResponseEntity.ok(new JWTResponse(autToken));
    }
}