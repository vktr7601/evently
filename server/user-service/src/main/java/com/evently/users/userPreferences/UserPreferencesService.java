package com.evently.users.userPreferences;

import com.evently.users.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPreferencesService {
    private final UserPreferencesRepository userPreferencesRepository;

    public void linkUserToPreferences(User user, List<Long> preferences) {
        List<UserPreferences> list = preferences.stream().map(pref -> {
            UserPreferences userPreferences = new UserPreferences();
            userPreferences.setUser(user);
            userPreferences.setEventCategoryId(pref);
            return userPreferences;
        }).toList();
        userPreferencesRepository.saveAll(list);
    }
}