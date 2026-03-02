package com.evently.events.infrastructure;

import com.evently.events.artists.dto.ArtistSeed;
import com.evently.events.artists.service.ArtistsService;
import com.evently.events.category.dto.CategorySeed;
import com.evently.events.category.service.CategoryService;
import com.evently.events.event.dto.EventSeed;
import com.evently.events.event.service.EventService;
import com.evently.events.eventsLocations.dto.EventLocationSeed;
import com.evently.events.eventsLocations.service.EventsLocationsService;
import com.evently.events.locations.dto.LocationSeed;
import com.evently.events.locations.service.LocationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final ObjectMapper objectMapper;

    @Bean
    CommandLineRunner initDatabase(
            ArtistsService artistsService,
            LocationService locationService,
            CategoryService categoryService,
            EventService eventService,
            EventsLocationsService eventsLocationsService) {
        return args -> {
            InputStream artistsJson = getClass().getResourceAsStream(
                    "/seed/artists.json");
            if (artistsJson == null) {
                throw new RuntimeException("Could not find artist" +
                        ".json in resources!");
            }

            List<ArtistSeed> artistList = objectMapper.readValue(
                    artistsJson,
                    new TypeReference<List<ArtistSeed>>() {}
            );
            artistsService.seedArtists(artistList);

            InputStream locationsJson = getClass().getResourceAsStream("/seed" +
                    "/locations.json");
            if (locationsJson == null) {
                throw new RuntimeException("Could not find locations" +
                        ".json in resources!");
            }
            List<LocationSeed> locationList = objectMapper.readValue(
                    locationsJson,
                    new TypeReference<List<LocationSeed>>() {}
            );

            locationService.seedLocations(locationList);

            InputStream categorySeed = getClass().getResourceAsStream("/seed" +
                    "/categories.json");
            if (categorySeed == null) {
                throw new RuntimeException("Could not find locations" +
                        ".json in resources!");
            }
            List<CategorySeed> categorySeeds = objectMapper.readValue(
                    categorySeed,
                    new TypeReference<List<CategorySeed>>() {}
            );

            categoryService.seedCategories(categorySeeds);

            InputStream eventsSeed = getClass().getResourceAsStream("/seed" +
                    "/events.json");
            if (eventsSeed == null) {
                throw new RuntimeException("Could not find events" +
                        ".json in resources!");
            }
            List<EventSeed> eventSeeds = objectMapper.readValue(
                    eventsSeed,
                    new TypeReference<List<EventSeed>>() {}
            );

            eventService.seedEvents(eventSeeds);
            InputStream eventLocations = getClass().getResourceAsStream(
                    "/seed" +
                            "/event-locations.json");
            if (eventLocations == null) {
                throw new RuntimeException("Could not find event-locations" +
                        ".json in resources!");
            }
            try{
                List<EventLocationSeed> eventLocationsSeed = objectMapper.readValue(
                        eventLocations,
                        new TypeReference<List<EventLocationSeed>>() {}
                );
                eventsLocationsService.seedEventLocations(eventLocationsSeed);

            }catch (Exception e){
                System.out.println();
            }
        };
    }
}