package com.evently.users.follow.location.service;

import com.evently.users.follow.location.model.FollowLocation;
import com.evently.users.follow.location.repository.FollowLocationRepository;
import com.evently.users.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowLocationService {
    private final FollowLocationRepository locationToFollowRepository;

    @Transactional
    public List<FollowLocation> addLocationsPreferences(User user,
                                                        List<Long> preferences) {
        var x =
                locationToFollowRepository.findAllByUserId(user.getId()).stream().map(FollowLocation::getLocationId).toList();
        locationToFollowRepository.deleteAllById(x);

        if (preferences.isEmpty()) {
            return Collections.emptyList();
        }
        List<FollowLocation> list = preferences.stream()
                .map(prefId -> {
                    FollowLocation up = new FollowLocation();
                    up.setUser(user);
                    up.setLocationId(prefId);
                    return up;
                })
                .peek(up -> log.info("Mapped UserPreference: {} created", up))
                .toList();

        List<FollowLocation> userPreferences =
                locationToFollowRepository.saveAll(list);
        log.info("Saved UserPreferences: {}", userPreferences);

        return userPreferences;
    }
}