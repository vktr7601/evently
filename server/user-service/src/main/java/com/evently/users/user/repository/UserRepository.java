package com.evently.users.user.repository;

import com.evently.users.user.model.User;
import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}