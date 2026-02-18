//package com.evently.events.contollers;
//
//import com.evently.events.artists.Artist;
//import com.evently.events.category.entities.CategoryDto;
//import com.evently.events.event.EventService;
//import com.evently.events.event.EventsController;
//import com.evently.events.event.entities.CreateEventRequest;
//import com.evently.events.event.entities.EventDetailDto;
//import com.evently.events.event.entities.EventListItemDto;
//import com.evently.events.event.entities.UpdateEventRequest;
//import com.evently.events.eventsLocations.entities.EventsLocationsDto;
//import com.evently.events.eventsLocations.entities.EventsLocationsStatus;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import exceptions.ResourceNotFoundException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(EventsController.class)
//@ExtendWith(MockitoExtension.class)
//public class EventsControllerTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @InjectMocks
//    private EventService eventService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private Artist createArtist(Long id, String name) {
//        Artist artist = new Artist();
//        artist.setId(id);
//        artist.name = name;
//        artist.imageUrl = "https://example.com/" + name + ".jpg";
//        return artist;
//    }
//
//    private EventListItemDto createEventListItem(long id, String name, Artist artist) {
//        EventListItemDto dto = new EventListItemDto(id, name, "Description for " + name, artist.imageUrl, artist);
//        dto.setCategoryDtoList(List.of(new CategoryDto("Music", 1L)));
//        return dto;
//    }
//
//    private EventDetailDto createEventDetail(long id, String name, Artist artist) {
//        EventDetailDto dto = new EventDetailDto(id, name, "Description for " + name, artist.imageUrl, artist);
//        dto.setCategoryDtoList(List.of(new CategoryDto("Music", 1L)));
//        dto.setEventLocationData(List.of(
//            new EventsLocationsDto(100L, id, 1L, name, "Madison Square Garden",
//                LocalDateTime.of(2026, 6, 15, 20, 0), BigDecimal.valueOf(49.99),
//                EventsLocationsStatus.AVAILABLE, 500)
//        ));
//        return dto;
//    }
//
//    // GET /events
//
//    @Test
//    void getAllEvents_returnsListOfEvents() throws Exception {
//        Artist artist = createArtist(1L, "Artist One");
//        List<EventListItemDto> events = List.of(
//            createEventListItem(1L, "Summer Concert", artist),
//            createEventListItem(2L, "Winter Gala", artist)
//        );
//        when(eventService.findAllEventsSortedByDateDesc()).thenReturn(events);
//
//        mockMvc.perform(get("/events"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$", hasSize(2)))
//            .andExpect(jsonPath("$[0].name", is("Summer Concert")))
//            .andExpect(jsonPath("$[1].name", is("Winter Gala")));
//
//        verify(eventService).findAllEventsSortedByDateDesc();
//    }
//
//    @Test
//    void getAllEvents_returnsEmptyList() throws Exception {
//        when(eventService.findAllEventsSortedByDateDesc()).thenReturn(Collections.emptyList());
//
//        mockMvc.perform(get("/events"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$", hasSize(0)));
//    }
//
//    // GET /events/{id}
//
//    @Test
//    void getEventDetails_returnsEventDetail() throws Exception {
//        Artist artist = createArtist(1L, "Artist One");
//        EventDetailDto detail = createEventDetail(1L, "Summer Concert", artist);
//        when(eventService.findEventDetailsById(1L)).thenReturn(detail);
//
//        mockMvc.perform(get("/events/1"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.name", is("Summer Concert")))
//            .andExpect(jsonPath("$.eventLocations", hasSize(1)))
//            .andExpect(jsonPath("$.eventLocations[0].locationName", is("Madison Square Garden")))
//            .andExpect(jsonPath("$.categories", hasSize(1)))
//            .andExpect(jsonPath("$.categories[0].name", is("Music")));
//
//        verify(eventService).findEventDetailsById(1L);
//    }
//
//    @Test
//    void getEventDetails_notFound_returns404() throws Exception {
//        when(eventService.findEventDetailsById(999L))
//            .thenThrow(new ResourceNotFoundException("Event not found with id: 999"));
//
//        mockMvc.perform(get("/events/999"))
//            .andExpect(status().isNotFound());
//    }
//
//    // GET /events/filter?category=
//
//    @Test
//    void filterEventsByCategory_returnsList() throws Exception {
//        Artist artist = createArtist(1L, "Artist One");
//        List<EventListItemDto> events = List.of(createEventListItem(1L, "Jazz Night", artist));
//        when(eventService.findAllEventsByCategoryName("Music")).thenReturn(events);
//
//        mockMvc.perform(get("/events/filter").param("category", "Music"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$", hasSize(1)))
//            .andExpect(jsonPath("$[0].name", is("Jazz Night")));
//
//        verify(eventService).findAllEventsByCategoryName("Music");
//    }
//
//    @Test
//    void filterEventsByCategory_noParam_returnsAll() throws Exception {
//        when(eventService.findAllEventsByCategoryName(null)).thenReturn(Collections.emptyList());
//
//        mockMvc.perform(get("/events/filter"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$", hasSize(0)));
//
//        verify(eventService).findAllEventsByCategoryName(null);
//    }
//
//    // POST /events
//
//    @Test
//    void createEvent_returns201() throws Exception {
//        Artist artist = createArtist(1L, "Artist One");
//        EventDetailDto createdEvent = createEventDetail(1L, "New Festival", artist);
//        when(eventService.createEvent(any(CreateEventRequest.class))).thenReturn(createdEvent);
//
//        String requestBody = """
//            {
//                "event_name": "New Festival",
//                "description": "A great festival",
//                "artist_id": 1,
//                "categories": [1],
//                "eventLocations": [
//                    {
//                        "location": 1,
//                        "date": "2026-06-15T20:00:00",
//                        "tickets": 500,
//                        "price": 49.99
//                    }
//                ]
//            }
//            """;
//
//        mockMvc.perform(post("/events")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody))
//            .andExpect(status().isCreated())
//            .andExpect(jsonPath("$.name", is("New Festival")))
//            .andExpect(jsonPath("$.eventLocations", hasSize(1)));
//
//        verify(eventService).createEvent(any(CreateEventRequest.class));
//    }
//
//    // PUT /events
//
//    @Test
//    void updateEvent_returns200() throws Exception {
//        doNothing().when(eventService).updateEvent(any(UpdateEventRequest.class));
//
//        String requestBody = """
//            {
//                "eventId": 1,
//                "eventName": "Updated Event",
//                "description": "Updated description",
//                "artistId": 1,
//                "categories": [1, 2],
//                "eventLocations": []
//            }
//            """;
//
//        mockMvc.perform(put("/events")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody))
//            .andExpect(status().isOk());
//
//        verify(eventService).updateEvent(any(UpdateEventRequest.class));
//    }
//
//    // GET /events/{id}/location
//
//    @Test
//    void getLocation_returnsLocationData() throws Exception {
//        EventsLocationsDto location = new EventsLocationsDto(
//            100L, 1L, 1L, "Summer Concert", "Madison Square Garden",
//            LocalDateTime.of(2026, 6, 15, 20, 0), BigDecimal.valueOf(49.99),
//            EventsLocationsStatus.AVAILABLE, 500
//        );
//        when(eventService.getEventLocationData(1L)).thenReturn(location);
//
//        mockMvc.perform(get("/events/1/location"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.locationName", is("Madison Square Garden")))
//            .andExpect(jsonPath("$.eventLocationId", is(100)))
//            .andExpect(jsonPath("$.eventsLocationsStatus", is("AVAILABLE")))
//            .andExpect(jsonPath("$.ticketsCount", is(500)));
//
//        verify(eventService).getEventLocationData(1L);
//    }
//}