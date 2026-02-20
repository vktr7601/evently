package com.evently.booking.ticket.entities;

import com.evently.booking.ticket.data.TicketStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Model used to display ticket information in list views.
 * Contains essential ticket details including event information, status, and pricing.
 */
@Getter
@Setter
@NoArgsConstructor
public class TicketListItem implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("number")
    private Long number;
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("eventLocationName")
    private String eventLocationName;
    @JsonProperty("status")
    private TicketStatus status;
    @JsonProperty("eventStartTime")
    private LocalDateTime eventStartTime;
    @JsonProperty("price")
    private BigDecimal price;
}