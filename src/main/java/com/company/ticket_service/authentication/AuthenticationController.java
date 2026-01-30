package com.company.ticket_service.authentication;

import com.company.ticket_service.account.AccountsService;
import com.company.ticket_service.account.entities.AccountRequest;
import com.company.ticket_service.authentication.models.AuthResponse;
import com.company.ticket_service.authentication.models.LoginRequest;
import com.company.ticket_service.core.config.security.jwt.JwtService;
import com.company.ticket_service.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AccountsService accountsService;

    @PostMapping("/register")
    public void registerUser(@RequestBody AccountRequest registerUserRequest) {
        if (accountRepository.existsByEmail(registerUserRequest.email())) throw new RuntimeException();

        accountsService.create(registerUserRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

            String token = jwtService.generateToken(loginRequest.email());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
