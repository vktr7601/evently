package com.company.ticket_service.event;

import com.company.ticket_service.event.dto.EventDto;
import com.company.ticket_service.event.dto.EventRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
  Event toEntity(EventRequest dto);

//  @Mapping(source = "entity.remainingTickets", target = "tickets")
//  @Mapping(source = "category", target = "categories")
  EventDto toDTO(Event entity, List<String> category);
}