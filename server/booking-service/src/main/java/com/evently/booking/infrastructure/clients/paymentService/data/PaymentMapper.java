package com.evently.booking.infrastructure.clients.paymentService.data;

import com.evently.booking.order.dto.FinishOrderRequest;
import com.evently.booking.order.model.Order;
import dto.payment.payment.PaymentRequest;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentRequest toPaymentRequest(FinishOrderRequest finishOrderRequest, Order order, String userEmail) {
        PaymentRequest paymentRequestObject = new PaymentRequest();
        paymentRequestObject.setOrderNumber(order.getNumber().toString());
        paymentRequestObject.setTransactionId(finishOrderRequest.getTransactionId());
        paymentRequestObject.setAmount(order.getTotalPrice());
        paymentRequestObject.setUserEmail(userEmail);
        paymentRequestObject.setUserId(order.getUserId());
        paymentRequestObject.setPaymentProvider("stripe");
        return paymentRequestObject;
    }
}