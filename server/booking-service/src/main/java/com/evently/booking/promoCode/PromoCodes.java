package com.evently.booking.promoCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import utils.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "promo_codes")
public class PromoCodes extends BaseEntity {
    @Column(name = "code")
    private String code;
    @Column(name = "discount_percentage")
    private double discountPercentage;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
}