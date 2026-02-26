package com.evently.users.follow.artist.service;

import com.evently.users.follow.artist.model.FollowArtist;
import com.evently.users.follow.artist.repository.FollowArtistRepository;
import com.evently.users.user.model.User;
import com.evently.users.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowArtistService {
    private final FollowArtistRepository artistFollowRepository;
    private final UserService userService;

    @Transactional
    public void followArtist(Long userId, Long artistId) {
        User user = userService.findById(userId);
        FollowArtist artistFollow = new FollowArtist();
        artistFollow.setUser(user);
        artistFollow.setArtistId(artistId);

        artistFollowRepository.save(artistFollow);
    }

    public void unfollowArtist(long userId, long artistId) {
        FollowArtist followArtist =
                artistFollowRepository.findByArtistIdAndUserId(artistId,
                        userId).orElseThrow(() -> new RuntimeException("You " +
                        "are not " + "following this artist"));

        artistFollowRepository.delete(followArtist);
    }

    public boolean isFollowed(long userId, long artistId) {
        return artistFollowRepository.findByArtistIdAndUserId(artistId,
                userId).isPresent();
    }
}