package com.evently.booking.infrastructure.exceptions;

import java.util.UUID;

public class OrderNotRefundableException extends Throwable {
    public OrderNotRefundableException(UUID uuid) {
        super(String.format("Order with number %d is not refundable", uuid.toString()));
    }
}