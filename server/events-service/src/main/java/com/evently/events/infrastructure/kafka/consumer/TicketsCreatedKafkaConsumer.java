package com.evently.events.infrastructure.kafka.consumer;

import com.evently.events.eventsLocations.service.EventsLocationsService;
import com.evently.events.processedEvent.ProcessedEvent;
import com.evently.events.processedEvent.ProcessedEventRepository;
import constants.KafkaTopics;
import events.ticket.TicketsCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketsCreatedKafkaConsumer {
    private final EventsLocationsService eventsLocationsService;
    private final ProcessedEventRepository processedEventRepository;


    @Transactional
    @KafkaListener(topics = KafkaTopics.TICKETS_CREATED)
    public void onTicketsCreated(TicketsCreated ticketsCreated) {
        if (processedEventRepository.existsById(ticketsCreated.getMessageId())) {
            log.info("Event {} already processed. Skipping.",
                    ticketsCreated.getMessageId());
            return;
        }
        try {
            eventsLocationsService.markAsActive(ticketsCreated);
            
            processedEventRepository.save(new ProcessedEvent(ticketsCreated.getMessageId(), Instant.now()));
        } catch (DataIntegrityViolationException e) {
            log.warn("Duplicate event detected during save: {}",
                    ticketsCreated.getMessageId());
        }
    }
}