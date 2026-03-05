package com.evently.booking.infrastructure.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromoCodeSeed implements Serializable {
    @JsonProperty("promoCode")
    private String promoCode;
    @JsonProperty("discountPercentage")
    private String discountPercentage;
    @JsonProperty("isActive")
    private boolean isActive;
    @JsonProperty("expiryDate")
    private LocalDateTime expiryDate;
    @JsonProperty("discountType")
    private String discountType;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("status")
    private String status;
}