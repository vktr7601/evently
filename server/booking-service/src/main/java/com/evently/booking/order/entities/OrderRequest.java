package com.evently.booking.order.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {
    @JsonProperty("event_location_id")
    private long eventLocationId;
    @JsonProperty("tickets_count")
    private int ticketsCount;
    @JsonProperty("date_time")
    private LocalDateTime dateTime;
    @JsonProperty("promo_code")
    private String promoCode;
}