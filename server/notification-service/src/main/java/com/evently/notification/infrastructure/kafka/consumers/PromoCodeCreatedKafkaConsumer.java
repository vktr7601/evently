package com.evently.notification.infrastructure.kafka.consumers;

import com.evently.notification.infrastructure.clients.UserServiceClient;
import com.evently.notification.notificationContent.contract.PromoCodeCreatedContentProvider;
import com.evently.notification.notificationContent.model.NotificationContent;
import com.evently.notification.notifications.service.NotificationService;
import com.evently.notification.processedEvent.ProcessedEvent;
import com.evently.notification.processedEvent.ProcessedEventRepository;
import constants.KafkaTopics;
import events.promoCode.PromoCodeCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PromoCodeCreatedKafkaConsumer {
    private final NotificationService notificationService;
    private final PromoCodeCreatedContentProvider promoCodeCreatedContentProvider;
    private final UserServiceClient userServiceClient;
    private final ProcessedEventRepository processedEventRepository;
    
    @Transactional
    @KafkaListener(topics = KafkaTopics.PROMO_CODE_CREATED)
    public void onPromoCodeCreated(PromoCodeCreated promoCodeCreated) {
        if (processedEventRepository.existsById(promoCodeCreated.getMessageId())) {
            log.info("Event {} already processed. Skipping.",
                    promoCodeCreated.getMessageId());
            return;
        }
        try {
            NotificationContent notificationContent =
                    promoCodeCreatedContentProvider.generateNotificationContent(promoCodeCreated);
            if (promoCodeCreated.getUserId() != null) {
                notificationService.createNotification(promoCodeCreated.getUserId(), notificationContent);
            } else {
                List<Long> users =
                        userServiceClient.getUserWithNotificationOn().getBody();
                if (!users.isEmpty()) {
                    for (Long userId : users) {
                        notificationService.createNotification(userId,
                                notificationContent);
                    }
                }
            }

            ProcessedEvent processedEvent =
                    processedEventRepository.save(new ProcessedEvent(promoCodeCreated.getMessageId(), Instant.now()));

        } catch (DataIntegrityViolationException e) {
            log.warn("Duplicate event detected during save: {}",
                    promoCodeCreated.getMessageId());
        }
    }
}