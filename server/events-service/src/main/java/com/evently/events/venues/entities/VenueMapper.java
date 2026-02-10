package com.evently.events.venues.entities;

import com.evently.events.venues.Venue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VenueMapper {
    VenueDto toDto(Venue venue);

    @Mapping(target = "imageUrl", source = "imageUrl", ignore = true)
    Venue toEntity(VenueRequest venueRequest);
}