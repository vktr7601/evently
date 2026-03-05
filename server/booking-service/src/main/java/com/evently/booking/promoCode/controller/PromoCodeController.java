package com.evently.booking.promoCode.controller;

import com.evently.booking.promoCode.model.dto.PromoCodeListItem;
import com.evently.booking.promoCode.service.PromoCodeService;
import constants.ApplicationHeaders;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/promo-codes")
public class PromoCodeController {
    private final PromoCodeService promoCodeService;

    @GetMapping("/me")
    public ResponseEntity<List<PromoCodeListItem>> getMyPromoCode(
            @RequestHeader(ApplicationHeaders.USER_ID) Long userId) {

        List<PromoCodeListItem> promoCodes =
                promoCodeService.findAllByUserId(userId);

        return ResponseEntity.ok(promoCodes);
    }

    @GetMapping("/validate")
    public ResponseEntity<PromoCodeListItem> validate(
            @RequestParam(name = "code") @NotBlank String code,
            @RequestHeader(ApplicationHeaders.USER_ID) long userId) {

        promoCodeService.validatePromoCode(code, userId);

        PromoCodeListItem promoCodeListItem =
                promoCodeService.getPromoCode(code);

        return ResponseEntity.ok(promoCodeListItem);
    }
//
//    // POST /api/v1/promo-codes/redeem
//    @PostMapping("/redeem")
//    public ResponseEntity<PromoCodeResponse> redeem(
//            @RequestBody @Valid RedeemPromoCodeRequest request,
//            @AuthenticationPrincipal UUID userId) {
//
//        PromoCode redeemed = promoCodeService.redeem(request.code(), userId);
//        return ResponseEntity.ok(PromoCodeResponse.from(redeemed));
//    }
}