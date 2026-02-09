package com.evently.users.userPreferences;

import com.evently.users.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class UserPreferencesRepositoryTests {
    private final UserPreferencesRepository userPreferencesRepository;
    private final TestEntityManager entityManager;

    @Autowired
    public UserPreferencesRepositoryTests(UserPreferencesRepository userPreferencesRepository, TestEntityManager entityManager) {
        this.userPreferencesRepository = userPreferencesRepository;
        this.entityManager = entityManager;
    }

    void userPreferencesSuccessfullySaved() {
        User user = entityManager.find(User.class, 1L);
    }
}