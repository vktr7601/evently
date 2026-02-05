package com.evently.events.performers;

import com.evently.events.performers.entities.PerformerDto;
import com.evently.events.performers.entities.PerformerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerformerService {
    private final PerformerRepository performerRepository;
    private final PerformerMapper performerMapper;

    public List<PerformerDto> findAll() {
        return performerRepository.findAll().stream().map(performerMapper::mapToDto).toList();
    }
}