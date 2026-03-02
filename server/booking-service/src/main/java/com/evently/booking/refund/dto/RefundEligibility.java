package com.evently.booking.refund.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefundEligibility implements Serializable {
    @JsonProperty("isEligible")
    private boolean isEligible;
    @JsonProperty("reason")
    private String reason;
}