package com.evently.booking.promoCode.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromoCodeRequest implements Serializable {
    @JsonProperty("promoCode")
    private String promoCode;
    @JsonProperty("discountPercentage")
    @Max(90)
    private Double discountPercentage;
    @JsonProperty("discountType")
    private String discountType;
    @JsonProperty("expiresAt")
    @Future
    private Instant expiryDate;
}