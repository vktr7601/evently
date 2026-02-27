package com.evently.booking.ticket.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EligibleToRefund implements Serializable {
    @JsonProperty("isRefundable")
    private boolean isRefundable;
}