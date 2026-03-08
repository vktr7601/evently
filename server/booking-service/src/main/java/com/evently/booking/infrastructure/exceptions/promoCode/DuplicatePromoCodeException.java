package com.evently.booking.infrastructure.exceptions.promoCode;

public class DuplicatePromoCodeException extends RuntimeException {
    public DuplicatePromoCodeException(String code) {
        super("Promo code '%s' already exists. Codes must be unique.".formatted(code));
    }
}