package com.evently.users.user.service;

import com.evently.users.exceptions.DuplicateEmailException;
import com.evently.users.exceptions.UserNotFoundException;
import com.evently.users.follow.category.service.FollowCategoryService;
import com.evently.users.follow.location.service.FollowLocationService;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final FollowCategoryService categoryFollowService;
    private final UsersMapper usersMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final FollowLocationService locationsToFollowService;

    @Transactional
    public User createUser(RegisterUser userRequest) throws DuplicateEmailException {
        if (userRepository.existsByEmail(userRequest.getEmail()))
            throw new DuplicateEmailException(userRequest.getEmail());

        User user = usersMapper.toUser(userRequest);
        userRepository.save(user);
        categoryFollowService.followCategories(user,
                userRequest.getCategories());
        locationsToFollowService.followLocations(user,
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
}