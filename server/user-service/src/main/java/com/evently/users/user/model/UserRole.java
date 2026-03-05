package com.evently.users.user.model;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public static UserRole convertFromString(String value) {
        for (UserRole role : values()) {
            if (role.value.equals(value)) {
                return role;
            }
        }
        return null;
    }
}