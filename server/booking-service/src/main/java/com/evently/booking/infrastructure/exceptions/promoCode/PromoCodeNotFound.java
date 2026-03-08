package com.evently.booking.infrastructure.exceptions.promoCode;

public class PromoCodeNotFound extends RuntimeException {
    public PromoCodeNotFound(String message) {
        super("PromoCode not found: " + message);
    }
}