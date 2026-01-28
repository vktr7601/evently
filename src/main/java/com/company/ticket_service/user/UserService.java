package com.company.ticket_service.user;

import com.company.ticket_service.auth.models.LoginRequest;
import com.company.ticket_service.user.dto.UserDto;
import com.company.ticket_service.user.dto.UserRequest;
import com.company.ticket_service.auth.models.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDto create(UserRequest request) {

        User dbRecord = userRepository.save(userMapper.toEntity(request));

        return userMapper.toDTO(dbRecord);
    }
}