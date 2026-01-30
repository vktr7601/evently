package com.company.ticket_service.account.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.util.List;

public record AccountRequest(
        @JsonProperty("first_name")
        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,
        @JsonProperty("last_name")
        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,
        @JsonProperty("age")
        @NotNull(message = "Age is required")
        @Min(value = 1, message = "Age must be at least 1")
        @Max(value = 150, message = "Age must be less than 150")
        int age,
        @JsonProperty("email")
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,
        @JsonProperty("password")
        @NotBlank(message = "Password is required")
        String password,
        @JsonProperty("preferences")
        List<String> preferences) {
}
