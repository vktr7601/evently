package com.company.ticket_service.account.entities;

import jakarta.validation.constraints.*;

public record AccountRequest(
        @NotBlank(message = "First name is required") @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters") String firstName,
        @NotBlank(message = "Last name is required") @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters") String lastName,
        @NotNull(message = "Age is required") @Min(value = 1, message = "Age must be at least 1") @Max(value = 150, message = "Age must be less than 150") Integer age,
        @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email) {
}