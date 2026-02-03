package com.evently.events.performers;

import lombok.RequiredArgsConstructor;
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
    public List<Performer> getPerformers() {
        return null;
    }
}