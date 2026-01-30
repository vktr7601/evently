package com.company.ticket_service.auth;

import com.company.ticket_service.account.Account;
import com.company.ticket_service.auth.models.AuthUser;
import com.company.ticket_service.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    @Cacheable(value = "users", key = "username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new AuthUser(account.getEmail(), account.getPassword(), account.getRole());
    }
}