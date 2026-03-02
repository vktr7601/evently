package com.evently.booking.ticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class TicketDetails implements Serializable {
    @JsonProperty("id")
    private long id;
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("eventLocationName")
    private String eventLocationName;
    @JsonProperty("eventStartTime")
    private LocalDateTime eventStartTime;
    @JsonProperty("confirmationNumber")
    private String confirmationNumber;
}