package com.evently.booking.promoCode.service;

import com.evently.booking.promoCode.repository.PromoCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;
}