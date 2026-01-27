package com.company.ticket_service.user;

import com.company.ticket_service.user.dto.UserDto;
import com.company.ticket_service.user.dto.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto toDTO(User user);

  User toEntity(UserRequest request);
}