package com.company.ticket_service.userPreferences;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPreferencesService {
    private final UserPreferencesRepository userPreferencesRepository;
}
