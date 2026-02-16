package com.evently.booking.ticket.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TicketsDto implements Serializable {
    private long id;
    private String eventName;
    private long eventLocationId;
    private LocalDateTime eventDate;
    private BigDecimal ticketPrice;
}