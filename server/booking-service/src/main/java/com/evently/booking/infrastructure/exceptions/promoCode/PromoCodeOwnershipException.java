package com.evently.booking.infrastructure.exceptions.promoCode;

public class PromoCodeOwnershipException extends RuntimeException {
    public PromoCodeOwnershipException(String code) {
        super("This promo code %s is not assigned to the current user.".formatted(code));
    }
}