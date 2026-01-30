package com.company.ticket_service.accountPreferences;

import com.company.ticket_service.account.entities.AccountMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountPreferencesService {
    private final AccountPreferencesRepository userPreferencesRepository;

    @KafkaListener(topics = "account_created", groupId = "sample-consumer-group")
    public void addUserPreferences(AccountMessage accountMessage) {
        System.out.println();
    }
}
