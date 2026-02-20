package com.evently.users.user.entities;

import com.evently.users.user.User;
import com.evently.users.user.auth.AuthUser;
import dtos.UserRegisteredEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "categoryFollowList", ignore = true)
    User toEntity(UserRequest user);

    AuthUser toAuthUser(User user);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    UserRegisteredEvent toUserRegisteredEvent(User user);
}