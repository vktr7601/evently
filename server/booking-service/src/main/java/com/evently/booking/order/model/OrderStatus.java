package com.evently.booking.order.model;

public enum OrderStatus {
    PENDING_PAYMENT,
    CONFIRMED,
    CANCELLED,
    EXPIRED,
    REFUNDED,
    PARTIALLY_REFUNDED;
}