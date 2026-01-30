package com.company.ticket_service.account.entities;

import com.company.ticket_service.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDTO(Account account);

    @Mapping(target = "password", ignore = true)
    Account toEntity(AccountRequest request);
}
