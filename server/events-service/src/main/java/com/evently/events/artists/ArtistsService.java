package com.evently.events.artists;

import com.evently.events.artists.entities.ArtistDetails;
import com.evently.events.artists.entities.ArtistListItem;
import com.evently.events.artists.entities.ArtistMapper;
import com.evently.events.config.S3BucketService;
import com.evently.events.eventsLocations.EventsLocationsService;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArtistsService {
    private final ArtistsRepository artistsRepository;
    private final S3BucketService s3BucketService;
    private final ArtistMapper mapper;
    private final EventsLocationsService eventsLocationsService;

    @Cacheable(cacheNames = "artists", key = "#id")
    public ArtistDetails findArtistDetails(long id) {
        ArtistDetails artistDetailsDto = artistsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artist with id " + id + " not found"));
        List<EventsLocationsDto> allUpcomingEventsByArtistId = eventsLocationsService.findAllUpcomingEventsByArtistId(artistDetailsDto.getId());

        artistDetailsDto.setLocations(allUpcomingEventsByArtistId);

        return artistDetailsDto;
    }

    @Cacheable(cacheNames = "artists", key = "'allArtistsSortedByDateDesc'")
    public List<ArtistListItem> findAllArtistsSortedByDateDesc() {
        List<ArtistListItem> allArtistsSortedByDateDesc = artistsRepository.findAllArtistsSortedByDateDesc();

        log.info("Found {} artists sorted", allArtistsSortedByDateDesc.size());

        return allArtistsSortedByDateDesc;
    }


    public Artist findById(Long id) {

        Artist artist = artistsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Artist with id " + id + " not found"));

        log.info("Found {} artist", artist.getName());

        return artist;
    }

//    public ArtistsDto savePerformer(ArtistRequest artistRequest) {
//        String imageUrl = "";
//        try {
//            imageUrl = s3BucketService.uploadFile(artistRequest.getImage());
//            var performer = mapper.mapToEntity(artistRequest);
//            performer.setImageUrl(imageUrl);
//            Artist savedEntity = artistsRepository.save(performer);
//            return mapper.mapToDto(performer);
//        } catch (Exception e) {
//            s3BucketService.deleteFile(imageUrl);
//        }
//        return null;
//    }
}