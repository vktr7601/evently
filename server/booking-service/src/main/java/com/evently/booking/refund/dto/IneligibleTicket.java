package com.evently.booking.refund.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IneligibleTicket implements Serializable {
    @JsonProperty("ticketNumber")
    private UUID ticketNumber;
    @JsonProperty("reason")
    private String reason;
}