package com.company.ticket_service.event.dto;

import java.util.List;

public record EventInfoDto(long id, String name, String description, List<String> classifications) {
}