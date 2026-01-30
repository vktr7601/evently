package com.company.ticket_service.notifications;

import com.company.ticket_service.authentication.models.AuthUser;
import com.company.ticket_service.notifications.entities.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationsController {

    private final NotificationService notificationService;


    @GetMapping
    public ResponseEntity<List<NotificationDto>> getNotifications(@AuthenticationPrincipal AuthUser authUser) {
        List<NotificationDto> n = notificationService.getNotificationsByEmail(authUser.getEmail());
        System.out.printf("getNotificationsByEmail: %s\n", authUser.getEmail());

        return ResponseEntity.ok(n);
    }
}
