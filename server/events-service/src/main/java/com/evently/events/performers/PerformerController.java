package com.evently.events.performers;

import com.evently.events.performers.entities.PerformerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}