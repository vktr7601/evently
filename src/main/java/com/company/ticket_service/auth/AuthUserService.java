package com.company.ticket_service.auth;

import com.company.ticket_service.auth.models.AuthUser;
import com.company.ticket_service.user.User;
import com.company.ticket_service.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Cacheable(value = "users", key = "username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new AuthUser(user.getEmail(), user.getPassword(), user.getRole());
    }
}