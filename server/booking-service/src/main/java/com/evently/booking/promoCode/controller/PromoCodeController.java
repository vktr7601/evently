package com.evently.booking.promoCode.controller;

import com.evently.booking.promoCode.model.PromoCode;
import com.evently.booking.promoCode.service.PromoCodeService;
import constants.ApplicationHeaders;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/promo-codes")
public class PromoCodeController {
    private final PromoCodeService promoCodeService;

    @GetMapping("/me")
    public ResponseEntity<PromoCodeResponse> getMyPromoCode(
            @RequestHeader(ApplicationHeaders.USER_ID) Long userId) {

        return promoCodeService.findByUserId(userId)
                .map(PromoCodeResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/promo-codes/validate?code=EVNT-A3F9X2
    @GetMapping("/validate")
    public ResponseEntity<PromoCodeValidationResponse> validate(
            @RequestParam @NotBlank String code,
            @AuthenticationPrincipal UUID userId) {

        PromoCodeValidationResponse response = promoCodeService.validate(code
                , userId);
        return ResponseEntity.ok(response);
    }

    // POST /api/v1/promo-codes/redeem
    @PostMapping("/redeem")
    public ResponseEntity<PromoCodeResponse> redeem(
            @RequestBody @Valid RedeemPromoCodeRequest request,
            @AuthenticationPrincipal UUID userId) {

        PromoCode redeemed = promoCodeService.redeem(request.code(), userId);
        return ResponseEntity.ok(PromoCodeResponse.from(redeemed));
    }
}