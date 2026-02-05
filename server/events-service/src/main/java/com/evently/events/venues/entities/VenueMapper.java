package com.evently.events.venues.entities;

import com.evently.events.venues.Venue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VenueMapper {
    VenueDto toDto(Venue venue);
}