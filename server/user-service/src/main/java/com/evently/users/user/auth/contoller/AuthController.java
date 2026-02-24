package com.evently.users.user.auth.contoller;

import com.evently.users.user.model.User;
import com.evently.users.user.service.UserService;
import com.evently.users.user.auth.dto.AuthResponse;
import com.evently.users.user.auth.service.AuthService;
import com.evently.users.user.auth.dto.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(
        origins = "http://localhost:3000",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        User user = userService.findByEmail(request.getEmail());
        String token = authService.generateToken(user,
                request.getPassword());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwtToken(token);
        authResponse.setUserRole(user.getUserRole().toString());

        return ResponseEntity.ok(authResponse);
    }
}