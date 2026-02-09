package com.evently.users.user;

import com.evently.users.user.entities.UserMapper;
import com.evently.users.user.entities.UserRequest;
import com.evently.users.userPreferences.UserPreferencesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserPreferencesService userPreferencesService;
    private final UserMapper userMapper;

    @Transactional
    public User registerUserWithPreferences(UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);

        userRepository.save(user);
        log.info("User  {} has been registered successfully", user);
        userPreferencesService.linkUserToPreferences(user, userRequest.getPreferences());

        return user;
    }
}