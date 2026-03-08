package com.evently.booking.promoCode.model;

import lombok.Getter;

@Getter
public enum PromoCodeStatus {
    ACTIVE("ACTIVE"),
    REDEEMED("REDEEMED"),
    EXPIRED("EXPIRED");
    private final String code;

    PromoCodeStatus(String code) {
        this.code = code;
    }

    public static PromoCodeStatus findByCode(String code) {
        for (PromoCodeStatus status : PromoCodeStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}