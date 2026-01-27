package com.company.ticket_service.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record EventRequest(
    @NotBlank(message = "Name field  cannot be empty") @JsonProperty(value = "name") String name,
    @JsonProperty(value = "categories") List<String> categories,
    @JsonProperty(value = "description") String description,
    @Min(1) @JsonProperty(value = "totalTickets") int totalTickets,
    @JsonProperty(value = "startDate") LocalDateTime eventDate,
    @Min(1) @JsonProperty(value = "ticketPrice") BigDecimal price) {}