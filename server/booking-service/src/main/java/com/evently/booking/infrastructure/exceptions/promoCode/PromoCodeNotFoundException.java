package com.evently.booking.infrastructure.exceptions.promoCode;

public class PromoCodeNotFoundException extends RuntimeException {
    public PromoCodeNotFoundException(String code) {
        super("Promo code %s not found.".formatted(code));
    }
}