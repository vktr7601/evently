package com.evently.users.user;

import com.evently.users.config.KakfaProducer;
import com.evently.users.exceptions.DuplicateEmailException;
import com.evently.users.user.entities.UserMapper;
import com.evently.users.user.entities.UserRequest;
import com.evently.users.userPreferences.UserPreferencesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserPreferencesService userPreferencesService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final KakfaProducer kafkaProducer;

    @Transactional
    public User createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail()))
            throw new DuplicateEmailException(userRequest.getEmail());

        User user = userMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        log.info("User  {} has been registered successfully", user);

        userPreferencesService.addPreferences(user, userRequest.getPreferences());

        kafkaProducer.sendUserRegisteredEvent(userMapper.toUserRegisteredEvent(user));

        return user;
    }
}