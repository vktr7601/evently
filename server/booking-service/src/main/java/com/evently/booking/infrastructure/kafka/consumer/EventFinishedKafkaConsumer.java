package com.evently.booking.infrastructure.kafka.consumer;

import com.evently.booking.ticket.TicketService;
import dtos.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventFinishedKafkaConsumer {
    private final TicketService ticketService;

    @KafkaListener(topics = KafkaTopics.EVENT_FINISHED)
    public void consumeEventLocationCancellationMessage(dtos.EventFinished eventCancelled) {
        int result =
                ticketService.discardAllUnboughtTickets(eventCancelled.getEventLocationId());

        log.info("Event cancelled with result {}", result);
    }
}