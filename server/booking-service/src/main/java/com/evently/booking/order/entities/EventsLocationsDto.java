package com.evently.booking.order.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventsLocationsDto {
    private long eventLocationId;
    private long eventId;
    private long locationId;
    private String eventName;
    private String locationName;
    private LocalDateTime eventStartTime;
    private Object eventsLocationsStatus;

    private BigDecimal pricePerTicket;
    private long ticketsCount;
}