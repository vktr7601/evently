package com.evently.notification.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @PostMapping("/preferences")
    List<Long> fetchUserIds(@RequestBody List<Long> data);

    @GetMapping("/user/notification-on")
    ResponseEntity<List<Long>> getUserWithNotificationOn();
}