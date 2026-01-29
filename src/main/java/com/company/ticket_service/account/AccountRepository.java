package com.company.ticket_service.account;

import com.company.ticket_service.core.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account> {

    @Override
    default String getEntityName() {
        return "User";
    }


    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);
}