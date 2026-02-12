package com.evently.users.util;

import com.evently.users.user.User;
import com.evently.users.user.entities.UserRole;
import net.datafaker.Faker;

public class DataGenerator {

    private final Faker faker = new Faker();

    public User generateRandomUserData() {
        User user = new User();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setAge(faker.number().numberBetween(18, 90));
        user.setEmail(faker.internet().emailAddress());
        user.setPassword("EncodedPassword123!");
        user.setUserRole(UserRole.USER);
        return user;
    }
}