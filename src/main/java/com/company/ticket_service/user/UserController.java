package com.company.ticket_service.user;

import com.company.ticket_service.user.dto.UserDto;
import com.company.ticket_service.user.dto.UserRequest;
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
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserRequest request) {
    var user = userService.create(request);

    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }
}