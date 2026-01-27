package com.company.ticket_service.category;

import com.company.ticket_service.category.entities.CategoryDto;
import com.company.ticket_service.category.entities.CategoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryRequest request) {
    var category = categoryService.create(request);
    return new ResponseEntity<>(category, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDto> getById(@PathVariable Long id) {
    return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
  }
}