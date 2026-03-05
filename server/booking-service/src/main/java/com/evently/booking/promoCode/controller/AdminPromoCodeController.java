package com.evently.booking.promoCode.controller;

import com.evently.booking.promoCode.model.dto.PromoCodeListItem;
import com.evently.booking.promoCode.model.dto.PromoCodeRequest;
import com.evently.booking.promoCode.service.PromoCodeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/promo-codes")
public class AdminPromoCodeController {
    private final PromoCodeService promoCodeService;

    @PostMapping
    public ResponseEntity<PromoCodeListItem> createPromoCode(@RequestBody PromoCodeRequest promoCode) {
        var promoCodeListItem = promoCodeService.create(promoCode);

        return ResponseEntity.ok(promoCodeListItem);
    }
}