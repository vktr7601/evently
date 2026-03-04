package com.evently.booking.promoCode.model;

import lombok.Getter;

@Getter
public enum DiscountType {
    PERCENTAGE("Percentage"),
    FLAT_AMOUNT("FlatAmount");

    private final String promoCode;

    DiscountType(String promoCode) {
        this.promoCode = promoCode;
    }
}