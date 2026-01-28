package com.company.ticket_service.user;

import com.company.ticket_service.core.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    @Override
    default String getEntityName() {
        return "User";
    }


    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}