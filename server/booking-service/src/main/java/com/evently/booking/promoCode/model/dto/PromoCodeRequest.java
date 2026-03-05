package com.evently.booking.promoCode.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromoCodeRequest implements Serializable {
    @JsonProperty("promoCode")
    private String promoCode;
    @JsonProperty("discountPercentage")
    private Double discountPercentage;
    @JsonProperty("discountType")
    private String discountType;
    @JsonProperty("expiresAt")
    private ZonedDateTime expiryDate;
}