package com.evently.users.user;

import com.evently.users.user.entities.UserMapper;
import com.evently.users.user.entities.UserRequest;
import com.evently.users.userPreferences.UserPreferencesRepository;
import com.evently.users.userPreferences.UserPreferencesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserPreferencesService userPreferencesService;
    private final UserPreferencesRepository userPreferencesRepository;
    private final UserMapper userMapper;

    void userRegister(User user) {
        userRepository.save(user);
    }

    Set<Long> getUserPreferences(List<Long> userPreferences) {
        Set<Long> userPreferencesSet = new HashSet<>();
        for (Long preferenceId : userPreferences) {
            userPreferencesSet.addAll(userPreferencesRepository.getUserPreferencesByEventCategoryId(preferenceId));
        }

        return userPreferencesSet;
    }

    boolean createUser(UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        userRepository.save(user);
        userPreferencesService.linkUserToPreferences(user, userRequest.getPreferences());

        return true;
    }
}