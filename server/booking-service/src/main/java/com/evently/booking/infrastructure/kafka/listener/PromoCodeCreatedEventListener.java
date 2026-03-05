package com.evently.booking.infrastructure.kafka.listener;

import com.evently.booking.infrastructure.kafka.producer.KafkaProducer;
import events.promoCode.PromoCodeCreated;
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
public class PromoCodeCreatedEventListener {
    private final KafkaProducer kafkaProducer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPromoCodeCreated(PromoCodeCreated promoCodeCreated) {
        promoCodeCreated.setMessageId(UUID.randomUUID().toString());
        promoCodeCreated.setOccurredAt(Instant.now());
        kafkaProducer.sendPromoCodeCreated(promoCodeCreated);
    }
}