package com.evently.booking.infrastructure.exceptions.promoCode;

public class ExpiredPromoCodeException extends RuntimeException {
    public ExpiredPromoCodeException(String promoCode) {
        super("Following promo code : %s has expired".formatted(promoCode));
    }
}