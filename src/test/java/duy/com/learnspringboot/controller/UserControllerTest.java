package duy.com.learnspringboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import duy.com.learnspringboot.dto.request.user.UserCreationRequest;
import duy.com.learnspringboot.dto.response.user.UserResponse;
import duy.com.learnspringboot.exception.ErrorCode;
import duy.com.learnspringboot.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserCreationRequest userCreationRequest;

    private UserResponse userResponse;

    @BeforeEach
    public void beforeEach() {
        Mockito.clearInvocations(userService);
        Mockito.reset(userService);

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
    }

    @Test
    public void givenValidRequest_whenCreateUser_thenSuccess() throws Exception {
        String userCreationRequestStr = objectMapper.writeValueAsString(userCreationRequest);

        when(userService.createUser(any())).thenReturn(userResponse);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreationRequestStr)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("data.username").value(userResponse.getUsername()));
    }

    @Test
    public void givenUsernameLengthLessThan4Characters_whenCreateUser_thenBadRequest() throws Exception {
        userCreationRequest.setUsername("abc");
        String userCreationRequestStr = objectMapper.writeValueAsString(userCreationRequest);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCreationRequestStr)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(ErrorCode.VALIDATION_ERROR.getCode()));
    }
}
