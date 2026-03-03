package com.evently.booking.promoCode.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import persistence.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "promo_codes")
public class PromoCode extends BaseEntity {
    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column(name = "discount_percentage", nullable = false, precision = 5,
            scale = 2)
    private BigDecimal discountPercentage;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Future
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType;

    public boolean isValid() {
        return isActive && (expiryDate == null || expiryDate.isAfter(LocalDateTime.now()));
    }
}