package com.company.ticket_service.classification;

import com.company.ticket_service.classification.entities.ClassificationDto;
import com.company.ticket_service.classification.entities.ClassificationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/{id}")
  public ResponseEntity<ClassificationDto> getById(@PathVariable Long id) {
    return new ResponseEntity<>(classificationService.findById(id), HttpStatus.OK);
  }
}