package com.evently.payment.payments.paymentRefunds.service;

import com.evently.payment.payments.paymentRefunds.repository.PaymentRefundsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentRefundsService {
    private final PaymentRefundsRepository paymentRefundsRepository;
}