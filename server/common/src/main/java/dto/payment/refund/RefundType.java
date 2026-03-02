package dto.payment.refund;

import lombok.Getter;

@Getter
public enum RefundType {
    FULL_ORDER("FULL_ORDER"),
    PARTIAL_TICKET("PARTIAL_TICKET");

    private final String type;

    RefundType(String type) {
        this.type = type;
    }
}