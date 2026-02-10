package com.evently.events.performers;

import com.evently.events.performers.entities.PerformerDto;
import com.evently.events.performers.entities.PerformerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/performers")
@RequiredArgsConstructor
public class PerformerController {
    private final PerformerService performerService;

    @GetMapping
    public ResponseEntity<List<PerformerDto>> getAllPerformers() {
        List<PerformerDto> performerDtos = performerService.findAll();
        return ResponseEntity.ok(performerDtos);
    }

    @PostMapping
    public ResponseEntity<PerformerDto> createPerformer(@ModelAttribute PerformerRequest request) {
        PerformerDto performerDto = performerService.savePerformer(request);
        return new ResponseEntity<>(performerDto, HttpStatus.CREATED);
    }
}