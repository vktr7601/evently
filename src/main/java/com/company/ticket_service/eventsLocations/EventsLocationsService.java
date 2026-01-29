package com.company.ticket_service.eventsLocations;

import com.company.ticket_service.event.Event;
import com.company.ticket_service.event.dto.EventLocationData;
import com.company.ticket_service.event.dto.EventsLocationMapper;
import com.company.ticket_service.eventsLocations.entities.EventLocationsDto;
import com.company.ticket_service.eventsLocations.entities.EventOccurrenceDTO;
import com.company.ticket_service.location.Location;
import com.company.ticket_service.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EventsLocationsService {

    private final EventsLocationsRepository eventsLocationsRepository;
    private final LocationRepository locationRepository;
    private final EventsLocationMapper eventsLocationMapper;

    public List<EventLocationsDto> create(Event event, List<EventLocationData> eventLocationData) {
        List<String> locationsNames = eventLocationData.stream().map(EventLocationData::getLocation).toList();
        Map<String, Location> map = locationRepository.findAllByNameIn(locationsNames).stream().collect(Collectors.toMap(Location::getName, Function.identity()));

        List<EventsLocations> list = eventLocationData.stream().map(data -> eventsLocationMapper.toDto(event, data, map.get(data.getLocation()))).toList();

        eventsLocationsRepository.saveAll(list);

        return list.stream().map(eventsLocationMapper::toDto).toList();
    }

    @Cacheable(value = "eventClassification", key = "#classificationName")
    public List<EventOccurrenceDTO> getEventsByClassification(String classificationName) {
        return eventsLocationsRepository.findAllByClassificationName(classificationName);
    }

    @Cacheable(value = "eventClassification", key = "#location")
    public List<EventOccurrenceDTO> getEventsByLocation(String location) {
        return eventsLocationsRepository.finaAllByLocationName(location);
    }

    public List<EventLocationsDto> getLocationsBy(long id) {
        return eventsLocationsRepository.findAllByEventId(id);
    }

}
