package com.evently.users.follow.category.service;

import com.evently.users.follow.category.model.FollowCategory;
import com.evently.users.follow.category.repository.FollowCategoryRepository;
import com.evently.users.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class FollowCategoryService {
    private final FollowCategoryRepository followCategoryRepository;

    @Transactional
    public void followCategories(User user,
                                 List<Long> categories) {
        if (!Objects.isNull(categories) && !categories.isEmpty()) {
            List<FollowCategory> list = categories.stream()
                    .map(prefId -> {
                        FollowCategory up = new FollowCategory();
                        up.setUser(user);
                        up.setCategoryId(prefId);
                        return up;
                    })
                    .peek(up -> log.info("Mapped UserPreference: {} created",
                            up))
                    .toList();

            List<FollowCategory> userPreferences =
                    followCategoryRepository.saveAll(list);
        }
    }

    public List<Long> getAllUsersWithPreferences(List<Long> preferences) {
        if (preferences == null || preferences.isEmpty()) {
            return Collections.emptyList();
        }

        return followCategoryRepository.findFollowersByCategories(preferences);
    }

    public List<Long> findAllByUserId(Long userId) {
        return followCategoryRepository.findAllByUserId(userId);
    }
}