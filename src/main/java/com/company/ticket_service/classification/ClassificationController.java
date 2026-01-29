package com.company.ticket_service.classification;

import com.company.ticket_service.classification.entities.ClassificationDto;
import com.company.ticket_service.classification.entities.ClassificationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class ClassificationController {

    private final ClassificationService classificationService;

    @PostMapping
    public ResponseEntity<ClassificationDto> create(@Valid @RequestBody ClassificationRequest request) {
        var category = classificationService.create(request);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
}
