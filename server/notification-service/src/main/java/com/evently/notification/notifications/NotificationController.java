package com.evently.notification.notifications;

import com.evently.notification.notifications.entities.NotificationListItemDto;
import jwt.JWTUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final JWTUtility jwtUtility;
//
//    @GetMapping
//    public ResponseEntity<List<NotificationDto>> getUserNotification(@RequestHeader("x-user-id") long userId) {
//        System.out.println();
//        return ResponseEntity.ok(notificationService.getAllUserNotifications(userId));
//    }

//    @GetMapping
//    public ResponseEntity<List<NotificationDto>> getUserNotification(@RequestHeader("Authorization") String authHeader) {
//        System.out.println();
//        String token = authHeader.replace("Bearer ", "");
//        long l = jwtUtility.extractUserId(token);
//        return ResponseEntity.ok(notificationService.findAllByUserId(l));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<List<NotificationListItemDto>> getUserNotification(@PathVariable long id) {
        return ResponseEntity.ok(notificationService.findAllByUserId(id));
    }

    @PutMapping("/{notificationId}")
    public void markNotificationAsRead(@PathVariable long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}