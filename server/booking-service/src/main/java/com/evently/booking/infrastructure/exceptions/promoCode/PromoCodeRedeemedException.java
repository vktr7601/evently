package com.evently.booking.infrastructure.exceptions.promoCode;

public class PromoCodeRedeemedException extends RuntimeException {
    public PromoCodeRedeemedException(String code) {
        super("Promo code '" + code + "' has already been redeemed and cannot be used again.");
    }
}