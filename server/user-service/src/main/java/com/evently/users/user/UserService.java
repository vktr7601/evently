package com.evently.users.user;

import com.evently.users.categoryFollow.CategoryFollowService;
import com.evently.users.exceptions.DuplicateEmailException;
import com.evently.users.locationFollow.LocationsToFollowService;
import com.evently.users.user.entities.UserRequest;
import com.evently.users.user.entities.UsersMapper;
import dtos.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final CategoryFollowService categoryFollowService;
    private final UsersMapper usersMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final LocationsToFollowService locationsToFollowService;

    @Transactional
    public User createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail()))
            throw new DuplicateEmailException(userRequest.getEmail());

        User user = usersMapper.toUser(userRequest);
        userRepository.save(user);
        categoryFollowService.addPreferences(user, userRequest.getCategories());
        locationsToFollowService.addLocationsPreferences(user,
                userRequest.getLocations());
        log.info("User  {} has been registered successfully", user);


        if (user.isShouldReceiveNotification()) {
            UserRegisteredEvent userRegisteredEvent =
                    usersMapper.toUserRegisteredEvent(user);
            eventPublisher.publishEvent(userRegisteredEvent);
        }

        return user;
    }
}