package com.company.ticket_service.user;

import com.company.ticket_service.core.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User> {

  @Override
  default String getEntityName() {
    return "User";
  }
}