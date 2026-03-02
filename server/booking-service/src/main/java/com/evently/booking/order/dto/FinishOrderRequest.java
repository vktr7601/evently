package com.evently.booking.order.dto;

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
public class FinishOrderRequest implements Serializable {
    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("promoCode")
    private String promoCode;
}