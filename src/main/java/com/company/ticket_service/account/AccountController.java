package com.company.ticket_service.account;

import com.company.ticket_service.account.entities.AccountDto;
import com.company.ticket_service.account.entities.AccountRequest;
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
@RequestMapping("/accounts")
public class AccountController {

    private final AccountsService userService;

    @PostMapping
    public ResponseEntity<AccountDto> create(@Valid @RequestBody AccountRequest request) {
        var user = userService.create(request);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}