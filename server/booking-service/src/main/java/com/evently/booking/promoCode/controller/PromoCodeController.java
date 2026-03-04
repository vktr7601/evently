package com.evently.booking.promoCode.controller;

import com.evently.booking.promoCode.model.dto.PromoCodeListItem;
import com.evently.booking.promoCode.service.PromoCodeService;
import constants.ApplicationHeaders;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    // GET /api/v1/promo-codes/validate?code=EVNT-A3F9X2
//    @GetMapping("/validate")
//    public ResponseEntity<PromoCodeValidationResponse> validate(
//            @RequestParam @NotBlank String code,
//            @AuthenticationPrincipal UUID userId) {
//
//        PromoCodeValidationResponse response = promoCodeService.validate(code
//                , userId);
//        return ResponseEntity.ok(response);
//    }
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