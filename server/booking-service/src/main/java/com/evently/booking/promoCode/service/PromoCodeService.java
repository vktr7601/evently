package com.evently.booking.promoCode.service;

import com.evently.booking.infrastructure.exceptions.promoCode.ExpiredPromoCodeException;
import com.evently.booking.infrastructure.exceptions.promoCode.PromoCodeNotFoundException;
import com.evently.booking.infrastructure.exceptions.promoCode.PromoCodeOwnershipException;
import com.evently.booking.infrastructure.exceptions.promoCode.PromoCodeRedeemedException;
import com.evently.booking.promoCode.model.PromoCode;
import com.evently.booking.promoCode.model.PromoCodeStatus;
import com.evently.booking.promoCode.model.dto.PromoCodeListItem;
import com.evently.booking.promoCode.model.dto.mapper.PromoCodeMapper;
import com.evently.booking.promoCode.repository.PromoCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;
    private final PromoCodeMapper promoCodeMapper;
    private final ApplicationEventPublisher eventPublisher;


    public void validatePromoCode(String code, long userId) {
        PromoCode promoCode =
                promoCodeRepository.findByCode(code).orElseThrow(() -> new PromoCodeNotFoundException(code));

        if (promoCode.getUserId() != null && !promoCode.getUserId().equals(userId)) {
            throw new PromoCodeOwnershipException(code);
        }

        if (promoCode.getStatus() == PromoCodeStatus.EXPIRED || Instant.now().isAfter(promoCode.getExpiryDate())) {
            throw new ExpiredPromoCodeException(code);
        }

        if (promoCode.getStatus() == PromoCodeStatus.REDEEMED) {
            throw new PromoCodeRedeemedException(code);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateForUser(long userId) {
        PromoCode promoCode = promoCodeMapper.forUser(userId);
        promoCodeRepository.save(promoCode);

        eventPublisher.publishEvent(promoCodeMapper.toPromoCodeCreated(promoCode));
        return promoCode.getCode();
    }

    public List<PromoCodeListItem> findAllByUserId(long userId) {
        return promoCodeRepository.findAllByUserId(userId).stream().map(promoCodeMapper::toPromoCodeListItem).toList();
    }
}