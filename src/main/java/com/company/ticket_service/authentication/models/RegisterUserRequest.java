package com.company.ticket_service.authentication.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RegisterUserRequest(@JsonProperty("first_name") String firstName,
                                  @JsonProperty("last_name") String lastName, @JsonProperty("email") String email,
                                  @JsonProperty("password") String password, @JsonProperty("age") int age,
                                  @JsonProperty("preferences") List<String> preferences) {
}
