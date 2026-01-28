package com.company.ticket_service.eventClassification;

import java.util.List;

import com.company.ticket_service.core.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsClassificationsRepository extends BaseRepository<EventsClassifications> {


    List<EventsClassifications> findAllByEventId(Long eventId);
}