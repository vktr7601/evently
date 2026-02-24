package com.evently.booking.order.entities;

import com.evently.booking.order.model.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter(AccessLevel.PRIVATE)
public class OrderListItemDto {
    private long number;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private Instant createdAt;

    public OrderListItemDto(long number, BigDecimal totalPrice, OrderStatus orderStatus, Instant createdAt) {
        this.number = number;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }
}