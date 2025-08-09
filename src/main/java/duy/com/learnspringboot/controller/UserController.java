package duy.com.learnspringboot.controller;

import duy.com.learnspringboot.dto.request.user.UserCreationRequest;
import duy.com.learnspringboot.dto.request.user.UserUpdateRequest;
import duy.com.learnspringboot.dto.response.ApiResponse;
import duy.com.learnspringboot.dto.response.user.UserResponse;
import duy.com.learnspringboot.service.IUserService;
import duy.com.learnspringboot.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping(path = "/users")
public class UserController {
    IUserService userService;

    @PostMapping()
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest userCreationRequest) {
        UserResponse createdUser = this.userService.createUser(userCreationRequest);
        return new ApiResponse<>(HttpStatus.CREATED.value(), createdUser);
    }

    @GetMapping()
    public List<UserResponse> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable UUID userId) {
        return this.userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable UUID userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        return this.userService.updateUser(userId, userUpdateRequest);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable UUID userId) {
        this.userService.deleteUser(userId);
    }
}
