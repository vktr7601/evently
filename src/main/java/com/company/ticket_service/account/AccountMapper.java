package com.company.ticket_service.account;

import com.company.ticket_service.account.entities.AccountDto;
import com.company.ticket_service.account.entities.AccountRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDTO(Account account);

    Account toEntity(AccountRequest request);
}
