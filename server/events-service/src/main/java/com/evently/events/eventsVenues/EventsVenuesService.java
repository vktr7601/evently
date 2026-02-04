package com.evently.events.eventsVenues;


import com.evently.events.event.Event;
import com.evently.events.event.entities.EventLocationData;
import com.evently.events.eventsVenues.entities.EventsVenuesDto;
import com.evently.events.eventsVenues.entities.EventOccurrenceDTO;
import com.evently.events.eventsVenues.entities.EventsLocationMapper;
import com.evently.events.venues.Venue;
import com.evently.events.venues.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EventsVenuesService {
    private final EventsVenuesRepository eventsVenuesRepository;
    private final VenueRepository venueRepository;
    private final EventsLocationMapper eventsLocationMapper;

    public List<EventsVenuesDto> create(Event event, List<EventLocationData> eventLocationData) {
        List<String> locationsNames = eventLocationData.stream().map(EventLocationData::getLocation).toList();
        Map<String, Venue> map = venueRepository.findAllByNameIn(locationsNames).stream().collect(Collectors.toMap(Venue::getName, Function.identity()));

        List<EventsVenues> list = eventLocationData.stream().map(data -> eventsLocationMapper.toDto(event, data, map.get(data.getLocation()))).toList();

        eventsVenuesRepository.saveAll(list);

        return list.stream().map(eventsLocationMapper::toDto).toList();
    }

    //  @Cacheable(value = "eventClassification", key = "#classificationName")
//    public List<EventOccurrenceDTO> getEventsByClassification(String classificationName) {
//        return eventsVenuesRepository.findAllByCategoryName(classificationName);
//    }

    //  @Cacheable(value = "eventClassification", key = "#location")
    public List<EventOccurrenceDTO> getEventsByLocation(String location) {
        return eventsVenuesRepository.finaAllByLocationName(location);
    }

    public List<EventsVenuesDto> getLocationsBy(long id) {
        return eventsVenuesRepository.findAllByEventId(id);
    }

}