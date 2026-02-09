package com.evently.users.user.entities;

import com.evently.users.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "userPreferencesList", ignore = true)
    User toEntity(UserRequest user);
}