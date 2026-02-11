package com.evently.events.venues.entities;

import com.evently.events.venues.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDto toDto(Location location);

    @Mapping(target = "imageUrl", source = "imageUrl", ignore = true)
    Location toEntity(LocationRequest locationRequest);
}