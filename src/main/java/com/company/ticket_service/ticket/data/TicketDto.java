package com.company.ticket_service.ticket.data;

import java.time.LocalDateTime;

public record TicketDto(Long number, String eventName, LocalDateTime eventDate) {}