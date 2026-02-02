package com.company.bookingservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class HelloController {
    @GetMapping("")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok("Hello World from Spring Boot booking!");
    }
}
