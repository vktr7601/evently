package com.company.ticket_service.account;

import com.company.ticket_service.account.entities.AccountDto;
import com.company.ticket_service.account.entities.AccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountsService {
    private final AccountRepository accountRepository;
    private final AccountMapper userMapper;

    @Transactional
    public AccountDto create(AccountRequest request) {
        Account dbRecord = accountRepository.save(userMapper.toEntity(request));
        return userMapper.toDTO(dbRecord);
    }
}
