package com.evently.booking.ticket.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TicketDetailsDto {
    private long id;
    private String eventName;
    private String eventLocationName;
    private LocalDateTime eventDate;
    private String confirmationNumber;
}