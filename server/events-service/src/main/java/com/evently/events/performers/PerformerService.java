package com.evently.events.performers;

import com.evently.events.config.S3BucketService;
import com.evently.events.performers.entities.PerformerDto;
import com.evently.events.performers.entities.PerformerMapper;
import com.evently.events.performers.entities.PerformerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerformerService {
    private final PerformerRepository performerRepository;
    private final PerformerMapper performerMapper;
    private final S3BucketService s3BucketService;
    private final PerformerMapper mapper;

    public List<PerformerDto> findAll() {
        return performerRepository.findAll().stream().map(performerMapper::mapToDto).toList();
    }

    public PerformerDto savePerformer(PerformerRequest performerRequest) {
        String imageUrl = "";
        try {
            imageUrl = s3BucketService.uploadFile(performerRequest.getImage());
            var performer = mapper.mapToEntity(performerRequest);
            performer.setImageUrl(imageUrl);
            Performer savedEntity = performerRepository.save(performer);
            return mapper.mapToDto(performer);
        } catch (Exception e) {
            s3BucketService.deleteFile(imageUrl);
        }
        return null;
    }
}