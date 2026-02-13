package com.evently.users.categoryFollow;

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
    private final CategoryFollowRepository userPreferencesRepository;
    private final TestEntityManager entityManager;
    private User user;

    @Autowired
    public UserPreferencesRepositoryTests(CategoryFollowRepository userPreferencesRepository, TestEntityManager entityManager) {
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
        CategoryFollow prefs = new CategoryFollow();
        prefs.setUser(user);
        prefs.setEventCategoryId(categoryId);

        entityManager.persistAndFlush(prefs);
        entityManager.clear();

        List<Long> result = userPreferencesRepository.findFollowersByCategories(List.of(categoryId));

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(categoryId);
    }

    @Test
    void shouldReturnEmptyList_WhenNoPreferencesExistForId() {
        List<Long> result = userPreferencesRepository.findFollowersByCategories(List.of(999L));

        assertThat(result).isEmpty();
    }

    @Test
    void shouldSaveUserPreferences_WhenDataIsValid() {
        CategoryFollow prefs = new CategoryFollow();
        prefs.setUser(user);
        prefs.setEventCategoryId(100L);

        CategoryFollow savedPrefs = userPreferencesRepository.save(prefs);
        entityManager.flush();
        entityManager.clear();

        CategoryFollow found = entityManager.find(CategoryFollow.class, savedPrefs.getId());
        assertThat(found).isNotNull();
        assertThat(found.getUser().getId()).isEqualTo(user.getId());
        assertThat(found.getEventCategoryId()).isEqualTo(100L);
    }

    @Test
    void shouldSaveAllUserPreferences_WhenGivenAList() {
        // Arrange
        User secondUser = dataGenerator.generateRandomUserData();
        entityManager.persistAndFlush(secondUser);

        CategoryFollow user1 = new CategoryFollow();
        user1.setUser(user);
        user1.setEventCategoryId(101L);

        CategoryFollow user2 = new CategoryFollow();
        user2.setUser(secondUser);
        user2.setEventCategoryId(102L);

        List<CategoryFollow> preferencesToSave = List.of(user1, user2);

        List<CategoryFollow> savedPreferences = userPreferencesRepository.saveAll(preferencesToSave);
        entityManager.flush();
        entityManager.clear();

        assertThat(savedPreferences).hasSize(2);
        assertThat(savedPreferences).extracting(CategoryFollow::getEventCategoryId).containsExactlyInAnyOrder(101L, 102L);

        long count = userPreferencesRepository.count();
        assertThat(count).isEqualTo(2);
    }
}