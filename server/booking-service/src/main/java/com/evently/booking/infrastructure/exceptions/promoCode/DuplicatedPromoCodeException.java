package com.evently.booking.infrastructure.exceptions.promoCode;

public class DuplicatedPromoCodeException extends RuntimeException {
    public DuplicatedPromoCodeException(String message) {
        super("Duplicated promo code: " + message);
    }
}