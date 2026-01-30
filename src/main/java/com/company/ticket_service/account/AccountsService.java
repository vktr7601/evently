package com.company.ticket_service.account;

import com.company.ticket_service.account.entities.AccountDto;
import com.company.ticket_service.account.entities.AccountMapper;
import com.company.ticket_service.account.entities.AccountMessage;
import com.company.ticket_service.account.entities.AccountRequest;
import com.company.ticket_service.accountPreferences.AccountPreferences;
import com.company.ticket_service.accountPreferences.AccountPreferencesRepository;
import com.company.ticket_service.classification.Classification;
import com.company.ticket_service.classification.ClassificationRepository;
import com.company.ticket_service.classification.ClassificationService;
import com.company.ticket_service.notifications.Notification;
import com.company.ticket_service.notifications.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountsService {
    private final AccountRepository accountrepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, AccountMessage> kafkaTemplate;
    private final ClassificationRepository classificationRepository;
    private final AccountPreferencesRepository accountPreferencesRepository;
    private final NotificationRepository notificationRepository;

    public boolean create(AccountRequest request) {
        Account entity = accountMapper.toEntity(request);
        entity.setPassword(passwordEncoder.encode(request.password()));
        entity.setRole(AccountRoles.USER);
        Account account = accountrepository.save(entity);
        kafkaTemplate.send("account_created", new AccountMessage(entity.getId(), request.preferences()));

        List<AccountPreferences> accountPreferences = classificationRepository.findAllByNameIn(request.preferences()).stream().map(classification -> {
            AccountPreferences preferences = new AccountPreferences();
            preferences.setAccount(account);
            preferences.setClassification(classification);
            return preferences;
        }).toList();

        accountPreferencesRepository.saveAll(accountPreferences);
        Notification notification = new Notification();
        notification.setAccount(account);
        notification.setMessage("Welcome to the message");
        notificationRepository.save(notification);

        return true;
    }
}
