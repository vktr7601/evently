package com.evently.users.user;

import com.evently.users.user.entities.UserMapper;
import com.evently.users.user.entities.UserRequest;
import com.evently.users.categoryFollow.CategoryFollowService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Mock
    private CategoryFollowService userPreferencesService;
    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void userSucessfulluyCreated() {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("firstName");
        userRequest.setLastName("lastName");
        userRequest.setEmail("email");
        userRequest.setAge(30);
        userRequest.setPassword("password");
        userRequest.setCategories(List.of(1L, 2L));

        User user = new User();
        user.setFirstName("firstName");
        var result = userService.persistUser(userRequest);


        Assertions.assertNotNull(user, "User should not be null");
    }

}