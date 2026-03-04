package com.evently.events.infrastructure.clients.userService;

import com.evently.events.infrastructure.clients.userService.dto.UserPreferences;
import constants.ApplicationHeaders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/preferences")
    UserPreferences getUserPreferences(@RequestHeader(ApplicationHeaders.USER_ID) Long userId);
}