package com.evently.booking.promoCode.model.dto.mapper;

import com.evently.booking.promoCode.model.DiscountType;
import com.evently.booking.promoCode.model.PromoCode;
import com.evently.booking.promoCode.model.PromoCodeStatus;
import com.evently.booking.promoCode.model.dto.PromoCodeListItem;
import events.promoCode.PromoCodeCreated;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class PromoCodeMapper {
    public PromoCode forUser(long userId) {
        PromoCode promoCode = new PromoCode();
        promoCode.setUserId(userId);
        promoCode.setCode(generateCode());
        promoCode.setDiscountPercentage(BigDecimal.valueOf(15));
        promoCode.setDiscountType(DiscountType.PERCENTAGE);
        promoCode.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));
        promoCode.setStatus(PromoCodeStatus.ACTIVE);
        return promoCode;
    }

    public PromoCodeCreated toPromoCodeCreated(PromoCode promoCode) {
        PromoCodeCreated promoCodeCreated = new PromoCodeCreated();
        if (promoCode.getUserId() != null) {
            promoCodeCreated.setUserId(promoCode.getUserId());
        }
        promoCodeCreated.setDiscountPercentage(promoCode.getDiscountPercentage());
        promoCodeCreated.setDiscountType(promoCode.getDiscountType().getPromoCode());
        promoCodeCreated.setExpirationDate(promoCode.getExpiryDate());
        promoCodeCreated.setPromoCode(promoCode.getCode());
        return promoCodeCreated;
    }

    public PromoCodeListItem toPromoCodeListItem(PromoCode promoCode) {
        PromoCodeListItem promoCodeListItem = new PromoCodeListItem();
        promoCodeListItem.setCode(promoCode.getCode());
        promoCodeListItem.setDiscountPercentage(promoCode.getDiscountPercentage());
        promoCodeListItem.setDiscountType(promoCode.getDiscountType().getPromoCode());
        promoCodeListItem.setActive(promoCode.isActive());
        promoCodeListItem.setStatus(promoCode.getStatus().getCode());

        return promoCodeListItem;
    }

    private String generateCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder("EVNT-");
        for (int i = 0; i < 6; i++)
            sb.append(chars.charAt(random.nextInt(chars.length())));
        return sb.toString();
    }
}