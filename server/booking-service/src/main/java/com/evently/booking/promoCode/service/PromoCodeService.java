package com.evently.booking.promoCode.service;

import com.evently.booking.infrastructure.data.PromoCodeSeed;
import com.evently.booking.infrastructure.exceptions.promoCode.*;
import com.evently.booking.promoCode.model.DiscountType;
import com.evently.booking.promoCode.model.PromoCode;
import com.evently.booking.promoCode.model.PromoCodeStatus;
import com.evently.booking.promoCode.model.dto.PromoCodeListItem;
import com.evently.booking.promoCode.model.dto.PromoCodeRequest;
import com.evently.booking.promoCode.model.dto.mapper.PromoCodeMapper;
import com.evently.booking.promoCode.repository.PromoCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public PromoCodeListItem getPromoCode(String code) {
        PromoCode promoCode =
                promoCodeRepository.findByCode(code).orElseThrow(() -> new PromoCodeNotFoundException(code));
        return promoCodeMapper.toPromoCodeListItem(promoCode);
    }

    public List<PromoCodeListItem> findAllByUserId(long userId) {
        return promoCodeRepository.findAllByUserId(userId).stream().map(promoCodeMapper::toPromoCodeListItem).toList();
    }

    @Transactional
    public PromoCodeListItem create(PromoCodeRequest promoCodeRequest) {
        if (promoCodeRepository.existsPromoCodeByCode(promoCodeRequest.getPromoCode())) {
            throw new DuplicatedPromoCodeException(promoCodeRequest.getPromoCode());
        }

        PromoCode promoCode = promoCodeMapper.toPromoCode(promoCodeRequest);

        promoCodeRepository.save(promoCode);
        //raise an event
        return promoCodeMapper.toPromoCodeListItem(promoCode);
    }


    @Transactional
    public void seedPromoCodes(List<PromoCodeSeed> promoCodeSeeds) {
        List<PromoCode> promoCodes =
                promoCodeSeeds.stream().map(promoCodeMapper::toPromoCode).toList();

        promoCodeRepository.saveAll(promoCodes);
    }

    public BigDecimal applyPromoCode(BigDecimal totalPrice, String code) {
        PromoCode promoCode = promoCodeRepository.findByCode(code)
                .orElseThrow(() -> new PromoCodeNotFoundException(code));

        if (promoCode.getDiscountType() == DiscountType.PERCENTAGE) {
            BigDecimal multiplier = BigDecimal.ONE
                    .subtract(promoCode.getDiscountPercentage()
                            .divide(BigDecimal.valueOf(100)));
            return totalPrice.multiply(multiplier)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        if (promoCode.getDiscountType() == DiscountType.FLAT_AMOUNT) {
            return totalPrice.subtract(promoCode.getDiscountPercentage())
                    .setScale(2, RoundingMode.HALF_UP);
        }

        return totalPrice;
    }

    public void validateAmount(BigDecimal totalPrice, String code) throws InsufficientOrderAmountException {
        PromoCode promoCode =
                promoCodeRepository.findByCode(code).orElseThrow(() -> new PromoCodeNotFoundException(code));

        if (promoCode.getDiscountType() == DiscountType.FLAT_AMOUNT) {
            if (totalPrice.compareTo(promoCode.getDiscountPercentage()) < 0) {
                throw new InsufficientOrderAmountException(totalPrice,
                        promoCode.getDiscountPercentage());
            }
        }
    }

    @Transactional
    public void updatePromoCode(String code) {
        PromoCode promoCode =
                promoCodeRepository.findByCode(code).orElseThrow(() -> new PromoCodeNotFoundException(code));

        if (promoCode.getUserId() != null) {
            promoCode.setActive(false);
            promoCode.setStatus(PromoCodeStatus.REDEEMED);
            promoCodeRepository.save(promoCode);
        }
    }
}