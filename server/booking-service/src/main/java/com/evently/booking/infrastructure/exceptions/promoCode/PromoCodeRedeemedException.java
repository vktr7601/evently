package com.evently.booking.infrastructure.exceptions.promoCode;

public class PromoCodeRedeemedException extends RuntimeException {
    public PromoCodeRedeemedException(String code) {
        super("Redeemed promo code " + code);
    }
}