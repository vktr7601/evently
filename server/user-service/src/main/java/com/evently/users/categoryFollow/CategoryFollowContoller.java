package com.evently.users.categoryFollow;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/preferences")
public class CategoryFollowContoller {
    private final CategoryFollowService userPreferencesService;

    @PostMapping
    public List<Long> getUserIdForSpecificEventCategory(@RequestBody List<Long> preferencesIds) {
        return userPreferencesService.getAllUsersWithPreferences(preferencesIds);
    }
}