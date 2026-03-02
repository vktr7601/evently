package com.evently.booking.infrastructure.kafka.consumer;

import com.evently.booking.processedEvent.ProcessedEvent;
import com.evently.booking.processedEvent.ProcessedEventRepository;
import com.evently.booking.ticket.service.TicketService;
import constants.KafkaTopics;
import events.event.EventCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventCreatedKafkaConsumer {
    private final ProcessedEventRepository processedEventRepository;

    private final TicketService ticketService;

    @KafkaListener(topics = KafkaTopics.EVENT_CREATED)
    public void onEventCreated(EventCreated eventCreated) {
        if (processedEventRepository.existsById(eventCreated.getMessageId())) {
            log.info("Event {} already processed. Skipping.",
                    eventCreated.getMessageId());
            return;
        }
        try {
            ticketService.addTickets(eventCreated.getTicketsCreationEvents());

            processedEventRepository.save(new ProcessedEvent(eventCreated.getMessageId(), Instant.now()));
        } catch (DataIntegrityViolationException e) {
            log.warn("Duplicate event detected during save: {}",
                    eventCreated.getMessageId());
        }
    }
}