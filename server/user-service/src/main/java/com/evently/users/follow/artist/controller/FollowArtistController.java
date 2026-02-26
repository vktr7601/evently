package com.evently.users.follow.artist.controller;

import com.evently.users.follow.artist.service.FollowArtistService;
import com.evently.users.follow.dto.IsFollowingResponse;
import com.evently.users.user.model.User;
import com.evently.users.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follows/artist")
public class FollowArtistController {
    private final FollowArtistService followArtistService;
    private final UserService userService;

    @PostMapping("/{artistId}")
    public ResponseEntity<IsFollowingResponse> followArtist(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long artistId) {
        User user = userService.findById(userId);
        followArtistService.followArtist(user, artistId);

        return ResponseEntity.ok(new IsFollowingResponse(true));
    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<IsFollowingResponse> unfollowArtist(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long artistId) {

        followArtistService.unfollowArtist(userId, artistId);

        return ResponseEntity.ok(new IsFollowingResponse(false));
    }

    @GetMapping("/{artistId}/status")
    public ResponseEntity<IsFollowingResponse> checkFollowArtist(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long artistId) {

        boolean result = followArtistService.isFollowed(userId, artistId);
        IsFollowingResponse isFollowingResponse =
                new IsFollowingResponse(result);
        return ResponseEntity.ok(isFollowingResponse);
    }
}