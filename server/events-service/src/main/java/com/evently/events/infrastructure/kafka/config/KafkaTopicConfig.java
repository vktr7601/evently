package com.evently.events.infrastructure.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static constants.KafkaTopics.EVENT_CREATED;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic eventCreatedTopic() {
        return TopicBuilder.name(EVENT_CREATED).build();
    }
}