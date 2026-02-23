package com.evently.users.locationFollow;

import com.evently.users.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationsToFollowService {
    private final LocationToFollowRepository locationToFollowRepository;

    @Transactional
    public List<LocationFollow> addLocationsPreferences(User user,
                                                        List<Long> preferences) {
        var x =
                locationToFollowRepository.findAllByUserId(user.getId()).stream().map(LocationFollow::getLocationId).toList();
        locationToFollowRepository.deleteAllById(x);

        if (preferences.isEmpty()) {
            return Collections.emptyList();
        }
        List<LocationFollow> list = preferences.stream()
                .map(prefId -> {
                    LocationFollow up = new LocationFollow();
                    up.setUser(user);
                    up.setLocationId(prefId);
                    return up;
                })
                .peek(up -> log.info("Mapped UserPreference: {} created", up))
                .toList();

        List<LocationFollow> userPreferences =
                locationToFollowRepository.saveAll(list);
        log.info("Saved UserPreferences: {}", userPreferences);

        return userPreferences;
    }
}