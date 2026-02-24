package com.evently.users.user.auth.service;

import com.evently.users.exceptions.BadCredentialsException;
import com.evently.users.user.model.User;
import jwt.JWTUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JWTUtility jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public String generateToken(User user, String rawPassword) {
        validatePassword(rawPassword, user.getPassword());

        return jwtUtils.generateToken(
                user.getEmail(),
                user.getId(),
                List.of(user.getUserRole().toString())
        );
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new BadCredentialsException();
        }
    }
}