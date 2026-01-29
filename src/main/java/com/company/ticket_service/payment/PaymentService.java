package com.company.ticket_service.payment;

import com.company.ticket_service.payment.entities.PaymentProvider;
import com.company.ticket_service.payment.entities.PaymentRequest;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public boolean processPayment(PaymentRequest paymentRequest) {

        PaymentProvider paymentProvider = switch (paymentRequest.paymentProvider()) {
            case STRIPE -> {

            }
        }

    }

}