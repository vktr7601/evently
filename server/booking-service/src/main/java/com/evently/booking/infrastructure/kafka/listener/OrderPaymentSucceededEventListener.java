package com.evently.booking.infrastructure.kafka.listener;

import com.evently.booking.infrastructure.kafka.producer.KafkaProducer;
import events.order.OrderPaymentSucceededEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaymentSucceededEventListener {
    private final KafkaProducer kafkaProducer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void omOrderPaymentSucceed(OrderPaymentSucceededEvent orderPaymentSucceededEvent) {
        orderPaymentSucceededEvent.setMessageId(UUID.randomUUID().toString());
        orderPaymentSucceededEvent.setOccurredAt(Instant.now());
        kafkaProducer.sendOrderSuccessfullyFinished(orderPaymentSucceededEvent);
    }
}