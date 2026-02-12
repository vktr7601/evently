package com.evently.events.artists.entities;

import com.evently.events.artists.Artist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtistMapper {
    ArtistsDto mapToDto(Artist artist);

    Artist mapToEntity(ArtistRequest dto);
}