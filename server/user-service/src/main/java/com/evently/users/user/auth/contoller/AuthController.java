package com.evently.users.user.auth.contoller;

import com.evently.users.user.auth.dto.AuthResponse;
import com.evently.users.user.auth.dto.LoginRequest;
import com.evently.users.user.auth.service.AuthService;
import com.evently.users.user.model.User;
import com.evently.users.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        User user = userService.findByEmail(request.getEmail());
        String token = authService.generateToken(user,
                request.getPassword());
        userService.updateLastLoginDate(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwtToken(token);
        authResponse.setUserRole(user.getUserRole().toString());

        return ResponseEntity.ok(authResponse);
    }
}