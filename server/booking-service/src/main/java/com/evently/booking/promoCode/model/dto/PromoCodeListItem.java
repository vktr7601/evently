package com.evently.booking.promoCode.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromoCodeListItem implements Serializable {
    @JsonProperty("promoCode")
    private String code;
    @JsonProperty("discountPercentage")
    private BigDecimal discountPercentage;
    @JsonProperty("active")
    private boolean isActive;
    @JsonProperty("status")
    private String status;
    @JsonProperty("discountType")
    private String discountType;
}