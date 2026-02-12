package com.evently.events.artists;

import com.evently.events.artists.entities.ArtistDetailsdDto;
import com.evently.events.artists.entities.ArtistListItem;
import com.evently.events.artists.entities.ArtistMapper;
import com.evently.events.config.S3BucketService;
import com.evently.events.eventsLocations.EventsLocationsService;
import com.evently.events.eventsLocations.entities.EventsLocationsDto;
import exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistsService {
    private static final Logger log = LoggerFactory.getLogger(ArtistsService.class);
    private final ArtistsRepository artistsRepository;
    private final S3BucketService s3BucketService;
    private final ArtistMapper mapper;
    private final EventsLocationsService eventsLocationsService;

    public ArtistDetailsdDto findArtistDetails(long id) {

        ArtistDetailsdDto artistDetailsdDto = artistsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artist with id " + id + " not found"));
        List<EventsLocationsDto> allUpcomingEventsByArtistId = eventsLocationsService.findAllUpcomingEventsByArtistId(artistDetailsdDto.getId());

        artistDetailsdDto.setLocations(allUpcomingEventsByArtistId);

        return artistDetailsdDto;
    }

    public List<ArtistListItem> findAllArtistsSortedByDateDesc() {
        List<ArtistListItem> allArtistsSortedByDateDesc = artistsRepository.findAllArtistsSortedByDateDesc();

        log.info("Found {} artists sorted", allArtistsSortedByDateDesc.size());

        return allArtistsSortedByDateDesc;
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