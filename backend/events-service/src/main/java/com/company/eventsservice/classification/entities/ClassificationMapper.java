package com.company.eventsservice.classification.entities;

import com.company.eventsservice.classification.Classification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassificationMapper {
    Classification toEntity(ClassificationRequest dto);

    ClassificationDto toDto(Classification entity);
}