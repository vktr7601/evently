package com.company.ticket_service.event.entities;

import com.company.ticket_service.event.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEntity(EventRequestDto dto);
}
