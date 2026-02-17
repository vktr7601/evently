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
    private LocalDateTime eventDate;
    private BigDecimal price;

//    public TicketListItem(long id, Long number, String eventName, String eventLocationName, LocalDateTime eventDate, BigDecimal price) {
//        setId(id);
//        setNumber(number);
//        setEventName(eventName);
//        setEventLocationName(eventLocationName);
//        setEventDate(eventDate);
//        setPrice(price);
//    }
//
//    public TicketListItem() {
//
//    }
}