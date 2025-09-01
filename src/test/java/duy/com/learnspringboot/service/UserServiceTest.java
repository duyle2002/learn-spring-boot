package duy.com.learnspringboot.service;

import duy.com.learnspringboot.dto.request.user.UserCreationRequest;
import duy.com.learnspringboot.dto.response.user.UserResponse;
import duy.com.learnspringboot.entity.User;
import duy.com.learnspringboot.exception.BadRequestException;
import duy.com.learnspringboot.exception.ErrorCode;
import duy.com.learnspringboot.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private IUserService userService;

    @MockitoBean
    private UserRepository userRepository;

    private UserCreationRequest userCreationRequest;

    private UserResponse userResponse;

    private User user;

    @BeforeEach
    public void setUp() {
        // Clear any existing mock interactions
        Mockito.clearInvocations(userRepository);
        Mockito.reset(userRepository);

        this.userCreationRequest = UserCreationRequest.builder()
                .username("username123")
                .password("password@123")
                .firstName("FirstName")
                .lastName("LastName")
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .build();

        this.userResponse = UserResponse.builder()
                .username("username123")
                .id(UUID.randomUUID())
                .firstName("FirstName")
                .lastName("LastName")
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .roles(new HashSet<>())
                .build();

        this.user = User.builder()
                .id(UUID.randomUUID())
                .username("username123")
                .password("password@123")
                .firstName("FirstName")
                .lastName("LastName")
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .build();
    }

    @AfterEach
    public void afterEach() {
        Mockito.reset(userRepository);
    }

    @Test
    public void givenValidRequest_whenCreateUser_thenSuccess() {
        when(userRepository.existsByUsername(userCreationRequest.getUsername())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        UserResponse result = userService.createUser(userCreationRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(user.getId(), result.getId());
        Assertions.assertEquals(user.getUsername(), result.getUsername());
        Assertions.assertEquals(user.getFirstName(), result.getFirstName());
        Assertions.assertEquals(user.getLastName(), result.getLastName());
    }

    @Test
    public void givenExistedUsername_whenCreateUser_thenThrowException() {
        when(userRepository.existsByUsername(userCreationRequest.getUsername())).thenReturn(true);

        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> userService.createUser(userCreationRequest));

        Assertions.assertEquals(ErrorCode.USER_ALREADY_EXISTS.getMessage(), exception.getMessage());
        Assertions.assertEquals(ErrorCode.USER_ALREADY_EXISTS.getCode(), exception.getErrorCode().getCode());
    }
}
