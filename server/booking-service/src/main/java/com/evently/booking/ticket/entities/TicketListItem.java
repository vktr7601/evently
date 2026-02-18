package com.evently.booking.ticket.entities;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketListItem implements Serializable {
    private long id;
    private Long number;
    private String eventName;
    private String eventLocationName;
    private TicketStatus status;
    private LocalDateTime eventDate;
    private BigDecimal price;
}