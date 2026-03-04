package com.evently.booking.promoCode.service;

import com.evently.booking.promoCode.model.PromoCode;
import com.evently.booking.promoCode.model.PromoCodeStatus;
import com.evently.booking.promoCode.repository.PromoCodeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PromoCodeExpirationScheduler {
    private final PromoCodeRepository promoCodeRepository;

    @Scheduled(cron = "0 0 * * * *") // every hour
    @Transactional
    public void expireOldCodes() {
        List<PromoCode> expired = promoCodeRepository
                .findByStatusAndExpiryDateBefore(PromoCodeStatus.ACTIVE,
                        Instant.now());

        expired.forEach(c -> c.setStatus(PromoCodeStatus.EXPIRED));
        promoCodeRepository.saveAll(expired);
        log.info("Expired {} promo codes", expired.size());
    }
}