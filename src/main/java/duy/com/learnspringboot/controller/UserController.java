package duy.com.learnspringboot.controller;

import duy.com.learnspringboot.dto.request.user.UserCreationRequest;
import duy.com.learnspringboot.dto.request.user.UserUpdateRequest;
import duy.com.learnspringboot.dto.response.ApiResponse;
import duy.com.learnspringboot.entity.User;
import duy.com.learnspringboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest userCreationRequest) {
        User createdUser = this.userService.createUser(userCreationRequest);
        return new ApiResponse<>(HttpStatus.OK.value(), createdUser);
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable UUID userId) {
        return this.userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable UUID userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        return this.userService.updateUser(userId, userUpdateRequest);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable UUID userId) {
        this.userService.deleteUser(userId);
    }
}
