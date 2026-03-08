package com.evently.booking.promoCode.controller;

import com.evently.booking.promoCode.model.dto.PromoCodeListItem;
import com.evently.booking.promoCode.model.dto.PromoCodeRequest;
import com.evently.booking.promoCode.service.PromoCodeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<PromoCodeListItem>> getPromoCodes() {
        var promoCodeListItem = promoCodeService.getSystemPromoCodes();

        return new ResponseEntity<>(promoCodeListItem, HttpStatus.OK);
    }
}