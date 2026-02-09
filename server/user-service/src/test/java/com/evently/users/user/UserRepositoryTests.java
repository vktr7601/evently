package com.evently.users.user;

import com.evently.users.util.BaseClass;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTests extends BaseClass {
    private final UserRepository userRepository;
    private final TestEntityManager entityManager;
    private User user;

    @Autowired
    public UserRepositoryTests(UserRepository userRepository, TestEntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @BeforeEach
    public void beforeEach() {
        user = dataGenerator.generateRandomUserData();
    }

    @Test
    void shouldNotBeNull_when_repositoryIsInjected() {
        assertNotNull(userRepository, "UserRepository should not be null");
    }

    @Test
    void shouldThrowException_when_firstNameIsTooShort() {
        user.setFirstName("A");

        assertThrows(ConstraintViolationException.class, () -> userRepository.saveAndFlush(user), "Constraint exceptions is expected to be thrown, but it was not.");
    }

    @Test
    void shouldThrowException_when_firstNameIsTooLong() {
        user.setFirstName("A".repeat(51));

        assertThrows(ConstraintViolationException.class, () -> userRepository.saveAndFlush(user), "Constraint exceptions is expected to be thrown, but it was not.");
    }

    @Test
    void shouldThrowException_when_lastNameIsBlank() {
        user.setLastName("   ");

        assertThrows(ConstraintViolationException.class, () -> userRepository.saveAndFlush(user), "Constraint exceptions is expected to be thrown, but it was not.");
    }

    @Test
    void shouldThrowException_when_ageIsZero() {
        user.setAge(0);

        assertThrows(ConstraintViolationException.class, () -> userRepository.saveAndFlush(user), "Constraint exceptions is expected to be thrown, but it was not.");
    }

    @Test
    void shouldThrowException_when_ageIsTooHigh() {
        user.setAge(151);

        assertThrows(ConstraintViolationException.class, () -> userRepository.saveAndFlush(user), "Constraint exceptions is expected to be thrown, but it was not.");
    }

    @Test
    void shouldThrowException_when_emailFormatIsInvalid() {
        user.setEmail("not-an-email");

        assertThrows(ConstraintViolationException.class, () -> userRepository.saveAndFlush(user), "Constraint exceptions is expected to be thrown, but it was not.");
    }

    @Test
    void shouldReturnTrue_when_emailIsValid() {
        entityManager.persist(user);

        assertTrue(userRepository.existsByEmail(user.getEmail()), "User with email " + user.getEmail() + " should exists, but it does not exist");
    }

    @Test
    void shouldReturnFalse_when_emailIsNotValid() {
        entityManager.persist(user);

        assertFalse(userRepository.existsByEmail("non-existing-email"), "User with email non-existing-email should not exists, but it does exist");
    }

    @Test
    void shouldThrowException_when_emailIsNull() {
        user.setEmail(null);

        assertThrows(ConstraintViolationException.class, () -> userRepository.saveAndFlush(user), "Constraint exceptions is expected to be thrown, but it was not.");
    }

    @Test
    void shouldThrowException_when_passwordIsNull() {
        user.setPassword(null);

        assertThrows(ConstraintViolationException.class, () -> userRepository.saveAndFlush(user), "Constraint exceptions is expected to be thrown, but it was not.");
    }

    @Test
    void userSuccessfullyCreated_when_invokeSave() {
        User savedUser = userRepository.save(user);

        Assertions.assertNotNull(savedUser.getId(), "User with id " + savedUser.getId() + " should exist");
    }
}