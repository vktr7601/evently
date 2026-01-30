package com.company.ticket_service.account.entities;

import com.company.ticket_service.account.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDTO(Account account);

    Account toEntity(AccountRequest request);
}
