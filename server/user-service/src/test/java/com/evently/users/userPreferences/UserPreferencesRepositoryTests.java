package com.evently.users.userPreferences;

import com.evently.users.user.User;
import com.evently.users.util.BaseClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserPreferencesRepositoryTests extends BaseClass {
    private final UserPreferencesRepository userPreferencesRepository;
    private final TestEntityManager entityManager;
    private User user;

    @Autowired
    public UserPreferencesRepositoryTests(UserPreferencesRepository userPreferencesRepository, TestEntityManager entityManager) {
        this.userPreferencesRepository = userPreferencesRepository;
        this.entityManager = entityManager;
    }

    @BeforeEach
    void beforeEach() {
        user = dataGenerator.generateRandomUserData();
        entityManager.persistAndFlush(user);
    }

    @Test
    void shouldGetUserPreferencesByEventCategoryId_WhenPreferencesExist() {
        Long categoryId = 1L;
        UserPreferences prefs = new UserPreferences();
        prefs.setUser(user);
        prefs.setEventCategoryId(categoryId);

        entityManager.persistAndFlush(prefs);
        entityManager.clear();

        List<Long> result = userPreferencesRepository.findUserIdsByEventCategory(List.of(categoryId));

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(categoryId);
    }

    @Test
    void shouldReturnEmptyList_WhenNoPreferencesExistForId() {
        List<Long> result = userPreferencesRepository.findUserIdsByEventCategory(List.of(999L));

        assertThat(result).isEmpty();
    }

    @Test
    void shouldSaveUserPreferences_WhenDataIsValid() {
        UserPreferences prefs = new UserPreferences();
        prefs.setUser(user);
        prefs.setEventCategoryId(100L);

        UserPreferences savedPrefs = userPreferencesRepository.save(prefs);
        entityManager.flush();
        entityManager.clear();

        UserPreferences found = entityManager.find(UserPreferences.class, savedPrefs.getId());
        assertThat(found).isNotNull();
        assertThat(found.getUser().getId()).isEqualTo(user.getId());
        assertThat(found.getEventCategoryId()).isEqualTo(100L);
    }

    @Test
    void shouldSaveAllUserPreferences_WhenGivenAList() {
        // Arrange
        User secondUser = dataGenerator.generateRandomUserData();
        entityManager.persistAndFlush(secondUser);

        UserPreferences user1 = new UserPreferences();
        user1.setUser(user);
        user1.setEventCategoryId(101L);

        UserPreferences user2 = new UserPreferences();
        user2.setUser(secondUser);
        user2.setEventCategoryId(102L);

        List<UserPreferences> preferencesToSave = List.of(user1, user2);

        List<UserPreferences> savedPreferences = userPreferencesRepository.saveAll(preferencesToSave);
        entityManager.flush();
        entityManager.clear();

        assertThat(savedPreferences).hasSize(2);
        assertThat(savedPreferences).extracting(UserPreferences::getEventCategoryId).containsExactlyInAnyOrder(101L, 102L);

        long count = userPreferencesRepository.count();
        assertThat(count).isEqualTo(2);
    }
}