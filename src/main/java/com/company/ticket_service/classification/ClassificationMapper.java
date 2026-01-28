package com.company.ticket_service.classification;

import com.company.ticket_service.classification.entities.ClassificationDto;
import com.company.ticket_service.classification.entities.ClassificationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassificationMapper {
  Classification toEntity(ClassificationRequest dto);

  ClassificationDto toDto(Classification entity);
}