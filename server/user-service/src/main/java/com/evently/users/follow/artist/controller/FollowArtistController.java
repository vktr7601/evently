package com.evently.users.follow.artist.controller;

import com.evently.users.follow.artist.dto.FollowArtistResponse;
import com.evently.users.follow.artist.service.FollowArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artists/{artistId}/follow")
public class FollowArtistController {
    private final FollowArtistService followArtistService;

    @PostMapping
    public ResponseEntity<FollowArtistResponse> followArtist(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long artistId) {

        followArtistService.followArtist(userId, artistId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new FollowArtistResponse(artistId, "followed"));
    }

    @DeleteMapping
    public ResponseEntity<FollowArtistResponse> unfollowArtist(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long artistId) {

        followArtistService.unfollowArtist(userId, artistId);

        return ResponseEntity
                .ok(new FollowArtistResponse(artistId, "unfollowed"));
    }
}