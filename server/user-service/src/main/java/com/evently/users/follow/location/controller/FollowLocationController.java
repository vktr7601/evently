package com.evently.users.follow.location.controller;

import com.evently.users.follow.dto.IsFollowingResponse;
import com.evently.users.follow.location.service.FollowLocationService;
import com.evently.users.user.model.User;
import com.evently.users.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follows/locations")
public class FollowLocationController {
    private final FollowLocationService followLocationService;
    private final UserService userService;

    @PostMapping("/{locationId}")
    public ResponseEntity<IsFollowingResponse> followLocation(@RequestHeader(
            "X" +
                    "-User-Id") Long userId, @PathVariable Long locationId) {
        User user = userService.findById(userId);
        followLocationService.followLocation(user, locationId);

        return ResponseEntity.ok(new IsFollowingResponse(true));
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<IsFollowingResponse> unFollowLocation(@RequestHeader(
            "X-User-Id") Long userId, @PathVariable Long locationId) {
        followLocationService.unfollowLocation(userId, locationId);

        return ResponseEntity.ok(new IsFollowingResponse(false));
    }

    @GetMapping("/{locationId}/status")
    public ResponseEntity<IsFollowingResponse> checkLocationStatus(@RequestHeader("X-User" +
            "-Id") Long userId, @PathVariable Long locationId) {
        boolean result = followLocationService.isFollowed(userId, locationId);
        IsFollowingResponse isFollowingResponse =
                new IsFollowingResponse(result);
        return ResponseEntity.ok(isFollowingResponse);
    }
}