package com.evently.users.user.service;

import com.evently.users.exceptions.DuplicateEmailException;
import com.evently.users.exceptions.UserNotFoundException;
import com.evently.users.follow.artist.service.FollowArtistService;
import com.evently.users.follow.category.service.FollowCategoryService;
import com.evently.users.follow.location.service.FollowLocationService;
import com.evently.users.user.dto.UserDetails;
import com.evently.users.user.dto.UserPreferences;
import com.evently.users.user.dto.mapper.UsersMapper;
import com.evently.users.user.dto.request.RegisterUser;
import com.evently.users.user.model.User;
import com.evently.users.user.repository.UserRepository;
import events.user.UserRegisteredEvent;
import exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final FollowCategoryService followCategoryService;
    private final FollowLocationService followLocationService;
    private final FollowArtistService followArtistService;
    private final UsersMapper usersMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public User createUser(RegisterUser userRequest) throws DuplicateEmailException {
        if (userRepository.existsByEmail(userRequest.getEmail()))
            throw new DuplicateEmailException(userRequest.getEmail());

        User user = usersMapper.toUser(userRequest);
        userRepository.save(user);
        followCategoryService.followCategories(user,
                userRequest.getCategories());
        followLocationService.followLocations(user,
                userRequest.getLocations());
        log.info("User  {} has been registered successfully", user);


        if (user.isShouldReceiveNotification()) {
            UserRegisteredEvent userRegisteredEvent =
                    usersMapper.toUserRegisteredEvent(user);
            eventPublisher.publishEvent(userRegisteredEvent);
        }

        return user;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("dada"));
    }

    @Transactional
    public UserPreferences getUserPreferences(long userId) {
        List<Long> followCategoriesList =
                followCategoryService.findAllByUserId(userId);
        List<Long> followLocationsList =
                followLocationService.findAllByUserId(userId);
        List<Long> followArtistsList =
                followArtistService.findAllByUserId(userId);

        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setArtists(followArtistsList);
        userPreferences.setLocations(followLocationsList);
        userPreferences.setCategories(followCategoriesList);

        return userPreferences;
    }

    public UserDetails getUserDetails(Long userId) {
        User user =
                userRepository.findById(userId).get();

        UserDetails userDetails = usersMapper.toUserDetails(user);
        UserPreferences userPreferences = getUserPreferences(userId);
        userDetails.setUserPreferences(userPreferences);

        return userDetails;
    }

    @Transactional
    public void updateLastLoginDate(User user) {
        user.setLastLoggedIn(LocalDateTime.now());
        userRepository.save(user);
    }
}