package com.evently.users.userPreferences;

import com.evently.users.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserPreferencesService {
    private final UserPreferencesRepository userPreferencesRepository;

    @Transactional
    public List<UserPreferences> linkUserToPreferences(User user, List<Long> preferences) {
        if (preferences.isEmpty()) {
            return Collections.emptyList();
        }
        List<UserPreferences> list = preferences.stream()
            .map(prefId -> {
                UserPreferences up = new UserPreferences();
                up.setUser(user);
                up.setEventCategoryId(prefId);
                return up;
            })
            .peek(up -> log.debug("Mapped UserPreference: {}", up))
            .toList();

        List<UserPreferences> userPreferences = userPreferencesRepository.saveAll(list);
        log.debug("Saved UserPreferences: {}", userPreferences);

        return userPreferences;
    }

    List<Long> getAllUsersWithPreferences(List<Long> preferences) {
        if (preferences == null || preferences.isEmpty()) {
            return Collections.emptyList();
        }

        return userPreferencesRepository.findUserIdsByEventCategory(preferences);
    }
}