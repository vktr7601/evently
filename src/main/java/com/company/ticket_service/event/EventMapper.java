package com.company.ticket_service.event;

import com.company.ticket_service.event.dto.EventRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEntity(EventRequestDto dto);
}
