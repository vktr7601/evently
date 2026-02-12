package com.evently.users.userPreferences;

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
public class UserPreferencesServiceTests extends BaseClass {
    @Mock
    private UserPreferencesRepository userPreferencesRepository;

    @InjectMocks
    private UserPreferencesService userPreferencesService;
    private User user;


    @BeforeEach
    public void beforeEach() {
        user = dataGenerator.generateRandomUserData();
        user.setId(1L);
    }

    @Test
    void shouldReturnEmptyList_WhenPreferencesListIsEmpty() {
        List<UserPreferences> result = userPreferencesService.addPreferences(user, Collections.emptyList());

        Assertions.assertTrue(result.isEmpty(), "Empty list should be returned, but it was not empty");
    }

    @Test
    void shouldMapAndSave_WhenPreferencesListIsPopulated() {
        List<Long> categoryIds = List.of(101L, 102L);

        List<UserPreferences> result = userPreferencesService.addPreferences(user, categoryIds);

        // Assert

        assertThat("List equality without order",
            result, containsInAnyOrder(categoryIds.toArray()));
    }
}