package com.evently.booking.order.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest implements Serializable {
    @JsonProperty("eventLocationId")
    private long eventLocationId;
    @JsonProperty("ticketsCount")
    private int ticketsCount;
    @JsonProperty("eventStartTime")
    private LocalDateTime eventStartTime;
    @JsonProperty("promo_code")
    private String promoCode;
}