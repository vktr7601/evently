package com.evently.events.locations.entities;

import com.evently.events.locations.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(target = "imageUrl", source = "imageUrl", ignore = true)
    Location toEntity(LocationRequest locationRequest);
}