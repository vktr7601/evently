package com.evently.notification.notifications;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@FeignClient(name = "user-service")
public interface UsersClient {
    @PostMapping("/users/internal/getUsers")
    Set<Long> fetchUserIds(@RequestBody List<Long> data);
}