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


    public static DiscountType getFromString(String discountType) {
        for (DiscountType dt : values()) {
            if (dt.getPromoCode().equals(discountType)) {
                return dt;
            }
        }
        return null;
    }
}