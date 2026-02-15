package com.evently.booking.ticket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dtos.EventCreated;
import dtos.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final TicketService ticketService;

    @KafkaListener(topics = KafkaTopics.EVENT_CREATED)
    public void consumeMessage(EventCreated eventCreated) {
        ticketService.createTickets(eventCreated.getTicketAllocations());
    }

    @Bean

    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}