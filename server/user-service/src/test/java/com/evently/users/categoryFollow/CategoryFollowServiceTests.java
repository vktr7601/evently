package com.evently.users.categoryFollow;

import com.evently.users.user.User;
import com.evently.users.util.BaseClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@ExtendWith(MockitoExtension.class)
public class CategoryFollowServiceTests extends BaseClass {
    @Mock
    private CategoryFollowRepository userPreferencesRepository;

    @InjectMocks
    private CategoryFollowService userPreferencesService;
    private User user;


    @BeforeEach
    public void beforeEach() {
        user = dataGenerator.generateRandomUserData();
        user.setId(1L);
    }

    @Test
    void shouldReturnEmptyList_WhenPreferencesListIsEmpty() {
        List<CategoryFollow> result = userPreferencesService.addPreferences(user, Collections.emptyList());

        Assertions.assertTrue(result.isEmpty(), "Empty list should be returned, but it was not empty");
    }

    @Test
    void shouldMapAndSave_WhenPreferencesListIsPopulated() {
        List<Long> categoryIds = List.of(101L, 102L);

        List<CategoryFollow> result = userPreferencesService.addPreferences(user, categoryIds);

        // Assert

        assertThat("List equality without order",
            result, containsInAnyOrder(categoryIds.toArray()));
    }
}