package com.evently.events.performers.entities;

import com.evently.events.performers.Performer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PerformerMapper {
    PerformerDto mapToDto(Performer performer);

    Performer mapToEntity(PerformerRequest dto);
}