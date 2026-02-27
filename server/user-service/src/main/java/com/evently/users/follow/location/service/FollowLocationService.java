package com.evently.users.follow.location.service;

import com.evently.users.follow.location.model.FollowLocation;
import com.evently.users.follow.location.repository.FollowLocationRepository;
import com.evently.users.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowLocationService {
    private final FollowLocationRepository followLocationRepository;

    @Transactional
    public void followLocations(User user, List<Long> locations) {
        if (!Objects.isNull(locations) && !locations.isEmpty()) {
            List<Long> existingLocations =
                    followLocationRepository.findAllByUserId(user.getId());
            followLocationRepository.deleteAllById(existingLocations);
            List<FollowLocation> list = locations.stream().map(prefId -> {
                FollowLocation up = new FollowLocation();
                up.setUser(user);
                up.setLocationId(prefId);
                return up;
            }).peek(up -> log.info("Mapped UserPreference: {} created", up)).toList();

            followLocationRepository.saveAll(list);
        }
    }

    @Transactional
    public void followLocation(User user, long locationId) {
        FollowLocation locationFollow = new FollowLocation();
        locationFollow.setUser(user);
        locationFollow.setLocationId(locationId);

        followLocationRepository.save(locationFollow);
    }

    @Transactional
    public void unfollowLocation(long userId, long locationId) {
        FollowLocation followLocation =
                followLocationRepository.findByUserIdAndLocationId(userId,
                        locationId).orElseThrow(() -> new RuntimeException(
                        "Follow location not found"));

        followLocationRepository.delete(followLocation);
    }

    public boolean isFollowed(long userId, long locationId) {
        return followLocationRepository.findByUserIdAndLocationId(userId,
                locationId).isPresent();
    }

    public List<Long> findAllByUserId(long userId) {
        return followLocationRepository.findAllByUserId(userId);
    }
}