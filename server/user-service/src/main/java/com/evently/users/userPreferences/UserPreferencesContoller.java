package com.evently.users.userPreferences;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/preferences")
public class UserPreferencesContoller {
    private final UserPreferencesService userPreferencesService;

    @PostMapping
    public List<Long> getUserIdForSpecificEventCategory(@RequestBody List<Long> preferencesIds) {
        return userPreferencesService.getAllUsersWithPreferences(preferencesIds);
    }
}