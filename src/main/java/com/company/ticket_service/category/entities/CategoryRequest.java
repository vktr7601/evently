package com.company.ticket_service.category.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public record CategoryRequest(
    @NotBlank(message = "Category name cannot be empty")
        @Size(min = 3, max = 64, message = "Name should be between 5 and 64 characters in length")
        @JsonProperty(value = "name")
        String name) {}