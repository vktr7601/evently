package com.evently.users.user.service;

import com.evently.users.config.seed.models.UserPreferencesSeed;
import com.evently.users.config.seed.models.UserSeed;
import com.evently.users.exceptions.DuplicateEmailException;
import com.evently.users.exceptions.UserNotFoundException;
import com.evently.users.follow.artist.model.FollowArtist;
import com.evently.users.follow.artist.service.FollowArtistService;
import com.evently.users.follow.category.model.FollowCategory;
import com.evently.users.follow.category.service.FollowCategoryService;
import com.evently.users.follow.location.model.FollowLocation;
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
import persistence.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        followLocationService.followLocations(user, userRequest.getLocations());
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
        User user = userRepository.findById(userId).get();

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

    public List<Long> getUserWithNotificationOn() {
        List<User> users =
                userRepository.findAllByShouldReceiveNotification(true);

        return users.stream().map(BaseEntity::getId).toList();
    }

    @Transactional
    public void seedUser(List<UserSeed> userSeeds) {
        List<User> users = new ArrayList<>();
        for (UserSeed userSeed : userSeeds) {
            if (!userRepository.existsByEmail(userSeed.getEmail()))
                users.add(usersMapper.toUser(userSeed));
        }

        userRepository.saveAll(users);
    }

    @Transactional
    public void seedUserPreferences(List<UserPreferencesSeed> userPreferencesSeed) {
        for (UserPreferencesSeed seed : userPreferencesSeed) {
            System.out.println(seed.getUserId());
            User user =
                    userRepository.findById(seed.getUserId()).get();
            List<FollowArtist> followArtists = new ArrayList<>();
            for (int i = 0; i < seed.getFollowSeed().getArtists().size(); i++) {
                FollowArtist followArtist = new FollowArtist();
                followArtist.setUser(user);
                followArtist.setArtistId(seed.getFollowSeed().getArtists().get(i));
                followArtists.add(followArtist);
            }
            List<FollowLocation> followLocations = new ArrayList<>();
            for (int i = 0; i < seed.getFollowSeed().getLocations().size(); i++) {
                FollowLocation followLocation = new FollowLocation();
                followLocation.setUser(user);
                followLocation.setLocationId(seed.getFollowSeed().getLocations().get(i));
                followLocations.add(followLocation);
            }

            List<FollowCategory> followCategories = new ArrayList<>();
            for (int i = 0; i < seed.getFollowSeed().getCategories().size(); i++) {
                FollowCategory followCategory = new FollowCategory();
                followCategory.setUser(user);
                followCategory.setCategoryId(seed.getFollowSeed().getCategories().get(i));
                followCategories.add(followCategory);
            }
            followArtistService.saveAll(followArtists);
            followLocationService.saveAll(followLocations);
            followCategoryService.saveAll(followCategories);
        }
    }
}