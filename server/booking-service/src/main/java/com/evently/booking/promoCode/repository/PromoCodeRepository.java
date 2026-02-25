package com.evently.booking.promoCode.repository;

import com.evently.booking.promoCode.model.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {
}