package com.evently.booking.ticket.dto;

import com.evently.booking.ticket.model.TicketStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Model used to display ticket information in list views.
 * Contains essential ticket details including event information, status, and
 * pricing.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketListItem implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("number")
    private UUID number;
    @JsonProperty("eventLocationId")
    private long eventLocationId;
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