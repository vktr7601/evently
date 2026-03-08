package com.evently.events.infrastructure.kafka.consumer;

import com.evently.events.event.service.EventService;
import com.evently.events.eventsLocations.service.EventsLocationsService;
import com.evently.events.processedEvent.ProcessedEvent;
import com.evently.events.processedEvent.ProcessedEventRepository;
import constants.KafkaTopics;
import events.user.UserRegisteredEvent;
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
public class UserRegisteredKafkaConsumer {
    private final ProcessedEventRepository processedEventRepository;
    private final EventsLocationsService eventsLocationsService;
    private final EventService eventService;

    @Transactional
    @KafkaListener(topics = KafkaTopics.USER_REGISTERED)
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent) {
        if (processedEventRepository.existsById(userRegisteredEvent.getMessageId())) {
            log.info("Event {} already processed. Skipping.",
                    userRegisteredEvent.getMessageId());
            return;
        }
        try {
            eventService.generateFeed(userRegisteredEvent.getUserId());
            ProcessedEvent processedEvent =
                    processedEventRepository.save(new ProcessedEvent(userRegisteredEvent.getMessageId(), Instant.now()));
        } catch (DataIntegrityViolationException e) {
            log.warn("Duplicate event detected during save: {}",
                    userRegisteredEvent.getMessageId());
        }
    }
}