package com.evently.users.config;

import constants.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic userRegisteredTopic() {
        return TopicBuilder.name(KafkaTopics.USER_REGISTERED).partitions(3).replicas(1).build();
    }
}