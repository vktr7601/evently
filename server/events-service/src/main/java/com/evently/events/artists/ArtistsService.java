package com.evently.events.artists;

import com.evently.events.config.S3BucketService;
import com.evently.events.artists.entities.ArtistMapper;
import com.evently.events.artists.entities.ArtistsDto;
import com.evently.events.artists.entities.ArtistRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistsService {
    private final ArtistsRepository artistsRepository;
    private final ArtistMapper performerMapper;
    private final S3BucketService s3BucketService;
    private final ArtistMapper mapper;

    public List<ArtistsDto> findAll() {
        return artistsRepository.findAll().stream().map(performerMapper::mapToDto).toList();
    }

    public ArtistsDto savePerformer(ArtistRequest artistRequest) {
        String imageUrl = "";
        try {
            imageUrl = s3BucketService.uploadFile(artistRequest.getImage());
            var performer = mapper.mapToEntity(artistRequest);
            performer.setImageUrl(imageUrl);
            Artist savedEntity = artistsRepository.save(performer);
            return mapper.mapToDto(performer);
        } catch (Exception e) {
            s3BucketService.deleteFile(imageUrl);
        }
        return null;
    }
}