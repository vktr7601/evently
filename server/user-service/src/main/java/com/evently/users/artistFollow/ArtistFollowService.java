package com.evently.users.artistFollow;

import com.evently.users.user.User;
import com.evently.users.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtistFollowService {
    private final ArtistFollowRepository artistFollowRepository;
    private final UserService userService;


    @Transactional
    public void followArtist(Long userId, Long artistId) {
        if (artistFollowRepository.findByArtistIdAndUserId(userId, artistId).isPresent()) {
            return;
        }

        User user = userService.findById(userId);

        ArtistFollow artistFollow = new ArtistFollow();
        artistFollow.setUser(user);
        artistFollow.setArtistId(artistId);

        artistFollowRepository.save(artistFollow);
    }

    public void unfollowArtist(Long userId, long artistId) {

        ArtistFollow artistFollow = artistFollowRepository.findByArtistIdAndUserId(userId, artistId)
            .orElseThrow(() -> new RuntimeException("You are not following this artist"));

        artistFollowRepository.delete(artistFollow);
    }
}