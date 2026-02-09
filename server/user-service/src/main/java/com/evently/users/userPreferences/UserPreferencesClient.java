package com.evently.users.userPreferences;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserPreferencesClient {
    @PostMapping("/preferences")
    List<Long> fetchUserIds(@RequestBody List<Long> preferences);
}