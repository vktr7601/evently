package com.company.ticket_service.user;

import com.company.ticket_service.user.dto.UserDto;
import com.company.ticket_service.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Transactional
  public UserDto create(UserRequest request) {

    User dbRecord = userRepository.save(userMapper.toEntity(request));

    return userMapper.toDTO(dbRecord);
  }
}