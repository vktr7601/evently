package com.evently.booking.refund.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRefundEligibility implements Serializable {
    @JsonProperty("eligible")
    private boolean eligible;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("ineligibleTickets")
    private List<IneligibleTicket> ineligibleTickets;
}