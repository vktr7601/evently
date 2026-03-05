package com.evently.booking.promoCode.repository;

import com.evently.booking.promoCode.model.PromoCode;
import com.evently.booking.promoCode.model.PromoCodeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {
    Optional<PromoCode> findByCode(String code);
//
//    List<PromoCode> findByStatusAndExpiresAtBefore(PromoCodeStatus status,
//                                                   Instant now);

    List<PromoCode> findByStatusAndExpiryDateBefore(PromoCodeStatus status,
                                               Instant expiryDateAfter);

    List<PromoCode> findAllByUserId(Long userId);

    boolean existsPromoCodeByCode(String code);
}