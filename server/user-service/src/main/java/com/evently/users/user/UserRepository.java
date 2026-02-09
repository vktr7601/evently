package com.evently.users.user;

import org.springframework.stereotype.Repository;
import utils.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<User> {
    boolean existsByEmail(String email);

    @Override
    <S extends User> S save(S entity);
}