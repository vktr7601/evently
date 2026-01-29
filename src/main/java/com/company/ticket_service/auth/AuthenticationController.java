package com.company.ticket_service.auth;

import com.company.ticket_service.account.Account;
import com.company.ticket_service.auth.models.AuthResponse;
import com.company.ticket_service.auth.models.RegisterUserRequest;
import com.company.ticket_service.auth.models.LoginRequest;
import com.company.ticket_service.core.config.security.jwt.JwtService;
import com.company.ticket_service.account.AccountRoles;
import com.company.ticket_service.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public void registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        if (accountRepository.existsByEmail(registerUserRequest.email())) throw new RuntimeException();
        Account account = new Account();
        account.(registerUserRequest.email());
        account.setPassword(passwordEncoder.encode(registerUserRequest.password()));
        account.setFirstName(registerUserRequest.firstName());
        account.setLastName(registerUserRequest.lastName());
        account.setAge(registerUserRequest.age());
        account.setRole(AccountRoles.USER);
        accountRepository.save(account);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );

        if (!authentication.isAuthenticated()) {
            throw new RuntimeException();
        }

        String token = jwtService.generateToken(loginRequest.email());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
