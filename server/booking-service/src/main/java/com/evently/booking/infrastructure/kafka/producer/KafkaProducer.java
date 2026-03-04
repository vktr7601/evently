package com.evently.booking.infrastructure.kafka.producer;

import constants.KafkaTopics;
import events.order.OrderPaymentSucceededEvent;
import events.promoCode.PromoCodeCreated;
import events.ticket.TicketsCreated;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendTicketCreated(TicketsCreated ticketsCreated) {
        kafkaTemplate.send(KafkaTopics.TICKETS_CREATED, ticketsCreated);
    }

    public void sendOrderSuccessfullyFinished(OrderPaymentSucceededEvent orderPaymentSucceededEvent) {
        kafkaTemplate.send(KafkaTopics.ORDER_SUCCESS,
                orderPaymentSucceededEvent);
    }

    public void sendPromoCodeCreated(PromoCodeCreated promoCodeCreated) {
        kafkaTemplate.send(KafkaTopics.PROMO_CODE_CREATED, promoCodeCreated);
    }
}