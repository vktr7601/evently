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
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowLocationService {
    private final FollowLocationRepository locationToFollowRepository;

    @Transactional
    public List<FollowLocation> followLocations(User user,
                                                List<Long> locations) {

        if (Objects.isNull(locations) || locations.isEmpty()) {
            return Collections.emptyList();
        }
        var x =
                locationToFollowRepository.findAllByUserId(user.getId()).stream().map(FollowLocation::getLocationId).toList();
        locationToFollowRepository.deleteAllById(x);

        if (locations.isEmpty()) {
            return Collections.emptyList();
        }
        List<FollowLocation> list = locations.stream()
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