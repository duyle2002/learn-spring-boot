package duy.com.learnspringboot.controller;

import duy.com.learnspringboot.dto.request.user.UserCreationRequest;
import duy.com.learnspringboot.dto.request.user.UserRequestDTO;
import duy.com.learnspringboot.dto.request.user.UserUpdateRequest;
import duy.com.learnspringboot.dto.response.ResponseData;
import duy.com.learnspringboot.dto.response.ResponseError;
import duy.com.learnspringboot.entity.User;
import duy.com.learnspringboot.service.UserService;
import duy.com.learnspringboot.utils.UserStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public User createUser(@RequestBody UserCreationRequest userCreationRequest) {
        return this.userService.createUser(userCreationRequest);
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
