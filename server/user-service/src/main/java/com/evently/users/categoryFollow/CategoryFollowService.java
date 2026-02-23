package com.evently.users.categoryFollow;

import com.evently.users.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryFollowService {
    private final CategoryFollowRepository userPreferencesRepository;

    @Transactional
    public List<CategoryFollow> addPreferences(User user,
                                               List<Long> preferences) {
        if (preferences.isEmpty()) {
            return Collections.emptyList();
        }
        List<CategoryFollow> list = preferences.stream()
                .map(prefId -> {
                    CategoryFollow up = new CategoryFollow();
                    up.setUser(user);
                    up.setCategoryId(prefId);
                    return up;
                })
                .peek(up -> log.info("Mapped UserPreference: {} created", up))
                .toList();

        List<CategoryFollow> userPreferences =
                userPreferencesRepository.saveAll(list);
        log.info("Saved UserPreferences: {}", userPreferences);

        return userPreferences;
    }

    List<Long> getAllUsersWithPreferences(List<Long> preferences) {
        if (preferences == null || preferences.isEmpty()) {
            return Collections.emptyList();
        }

        return userPreferencesRepository.findFollowersByCategories(preferences);
    }
}