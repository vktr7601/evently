package com.evently.users.follow.category.contoller;

import com.evently.users.follow.category.service.FollowCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/preferences")
public class FollowCategoryController {
    private final FollowCategoryService followCategoryService;

    @PostMapping
    public List<Long> getUserIdForSpecificEventCategory(@RequestBody List<Long> preferencesIds) {
        return followCategoryService.getAllUsersWithPreferences(preferencesIds);
    }
}