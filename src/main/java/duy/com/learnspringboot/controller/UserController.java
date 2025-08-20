package duy.com.learnspringboot.controller;

import duy.com.learnspringboot.dto.request.user.UserCreationRequest;
import duy.com.learnspringboot.dto.request.user.UserUpdateRequest;
import duy.com.learnspringboot.dto.response.ApiResponse;
import duy.com.learnspringboot.dto.response.user.UserResponse;
import duy.com.learnspringboot.service.IUserService;
import duy.com.learnspringboot.utils.SecurityExpressions;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
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

    @PreAuthorize(SecurityExpressions.HAS_ROLE_ADMIN)
    @GetMapping()
    public List<UserResponse> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        UserResponse userResponse = this.userService.getMyInfo();
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .data(userResponse)
                .build();
    }

    @GetMapping("/{userId}")
//    @PreAuthorize("hasAuthority('CREATE_POST')")
    public ApiResponse<UserResponse> getUser(@PathVariable UUID userId) {
        UserResponse userResponse = this.userService.getUserById(userId);
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .data(userResponse)
                .build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable UUID userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        UserResponse updatedUser = this.userService.updateUser(userId, userUpdateRequest);
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .data(updatedUser)
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<Void> deleteUser(@PathVariable UUID userId) {
        this.userService.deleteUser(userId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Deleted user successfully")
                .build();
    }
}
