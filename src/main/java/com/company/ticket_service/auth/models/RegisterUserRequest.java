package com.company.ticket_service.auth.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterUserRequest(
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("email")
        String email,
        @JsonProperty("password")
        String password,
        @JsonProperty("age")
        int age) {
}