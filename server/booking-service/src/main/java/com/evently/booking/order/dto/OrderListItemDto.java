package com.evently.booking.order.dto;

import com.evently.booking.order.model.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class OrderListItemDto {
    @JsonProperty("number")
    private UUID number;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("orderStatus")
    private OrderStatus orderStatus;
    @JsonProperty("createdAt")
    private Instant createdAt;
}