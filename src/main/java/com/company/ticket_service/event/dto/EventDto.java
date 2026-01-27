package com.company.ticket_service.event.dto;

import java.time.LocalDateTime;
import java.util.List;

public record EventDto(long id,String name, LocalDateTime eventDate, int tickets, List<String> categories) {}