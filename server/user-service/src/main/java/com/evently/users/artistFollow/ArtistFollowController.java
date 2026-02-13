package com.evently.users.artistFollow;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/follow-artist")
@RequiredArgsConstructor
public class ArtistFollowController {
    private final ArtistFollowService followArtistService;


    @PostMapping("/{artistId}")
    public ResponseEntity<Map<String, Object>> followArtist(
        @RequestHeader("X-User-Id") Long userId,
        @PathVariable Long artistId) {

        followArtistService.followArtist(userId, artistId);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "followedArtistId", artistId,
            "status", "success"
        ));
    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<Map<String, Object>> deleteFollow(
        @RequestHeader("X-User-Id") Long userId,
        @PathVariable Long artistId) {

        followArtistService.unfollowArtist(userId, artistId);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "followedArtistId", artistId,
            "status", "unfollowed"
        ));
    }

}