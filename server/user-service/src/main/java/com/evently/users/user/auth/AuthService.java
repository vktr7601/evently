package com.evently.users.user.auth;

import com.evently.users.exceptions.BadCredentialsException;
import com.evently.users.exceptions.UserNotFoundException;
import com.evently.users.user.User;
import com.evently.users.user.UserRepository;
import com.evently.users.user.entities.UserMapper;
import jwt.JWTUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JWTUtility jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UserNotFoundException(username));

        AuthUser authUser = userMapper.toAuthUser(user);

        return authUser;
    }

    public String authenticateAndGenerateToken(String email, String password) {
        if (password == null || password.isEmpty()) {
            throw new BadCredentialsException();
        }

        AuthUser userDetails = (AuthUser) loadUserByUsername(email);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return jwtUtils.generateToken(userDetails.getUsername(), userDetails.getId(), List.of(userDetails.getUserRole().toString()));
        }

        throw new BadCredentialsException();
    }
}