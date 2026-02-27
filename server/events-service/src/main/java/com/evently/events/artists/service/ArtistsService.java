package com.evently.events.artists.service;

import com.evently.events.artists.dto.ArtistDetails;
import com.evently.events.artists.dto.ArtistListItem;
import com.evently.events.artists.dto.mapper.ArtistMapper;
import com.evently.events.artists.dto.request.ArtistRequest;
import com.evently.events.artists.model.Artist;
import com.evently.events.artists.repository.ArtistsRepository;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import com.evently.events.eventsLocations.service.EventsLocationsService;
import com.evently.events.eventsLocations.service.data.FetchMode;
import com.evently.events.infrastructure.s3.S3Folders;
import com.evently.events.infrastructure.s3.S3Service;
import exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArtistsService {
    private final ArtistsRepository artistsRepository;
    private final EventsLocationsService eventsLocationsService;
    private final S3Service s3Service;
    private final ArtistMapper artistMapper;

    //    @Cacheable(cacheNames = "artists", key = "#id")
    public ArtistDetails findArtistDetails(long id) {
        log.info("Getting artist details for id {}", id);
        ArtistDetails artistDetailsDto = artistsRepository.findArtistDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist with" +
                        " id " + id + "was not found"));

        log.info("Found artist details for id {}", id);
        List<EventsLocationsDto> artistEventLocations =
                eventsLocationsService.findAllUpcomingEventsByArtistId(artistDetailsDto.getId(), FetchMode.WITH_AVAILABILITY);
        log.info("Found artist details for id {}", artistDetailsDto.getId());
        artistDetailsDto.setEventLocations(artistEventLocations);

        log.info("Found artist details for id {}", artistDetailsDto.getId());
        return artistDetailsDto;
    }

    @Cacheable(cacheNames = "artists", key = "'allArtistsSortedByDateDesc'")
    public List<ArtistListItem> findAllArtistsSortedByDateDesc() {
        List<ArtistListItem> allArtistsSortedByDateDesc =
                artistsRepository.findAllArtistsSortedByDateDesc();

        log.info("Found {} artists sorted", allArtistsSortedByDateDesc.size());

        return allArtistsSortedByDateDesc;
    }


    @Transactional
    public Artist findById(Long id) throws ResourceNotFoundException {
        Artist artist = artistsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist with" +
                        " id " + id + " not found"));

        log.info("Found {} artist", artist.getName());

        return artist;
    }

    @Transactional
    public ArtistDetails createArtist(ArtistRequest artistRequest) {
        String imageUrl = s3Service.uploadFile(artistRequest.getImageUrl(),
                S3Folders.ARTISTS);
        Artist artist = artistMapper.toArtist(artistRequest, imageUrl);
        artistsRepository.save(artist);
        return artistMapper.toArtistDetails(artist);
    }
}