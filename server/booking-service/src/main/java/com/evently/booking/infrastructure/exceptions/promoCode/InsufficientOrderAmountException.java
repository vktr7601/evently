package com.evently.booking.infrastructure.exceptions.promoCode;

import java.math.BigDecimal;

public class InsufficientOrderAmountException extends RuntimeException {
    public InsufficientOrderAmountException(BigDecimal orderTotal,
                                            BigDecimal discountAmount) {
        super(String.format(
                "Order total of $%.2f is less than the discount amount of $%" +
                        ".2f",
                orderTotal, discountAmount
        ));
    }
}