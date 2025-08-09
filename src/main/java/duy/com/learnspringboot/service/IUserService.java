package duy.com.learnspringboot.service;

import duy.com.learnspringboot.dto.request.user.UserCreationRequest;
import duy.com.learnspringboot.dto.request.user.UserUpdateRequest;
import duy.com.learnspringboot.dto.response.user.UserResponse;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    UserResponse createUser(UserCreationRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(UUID userId);
    UserResponse updateUser(UUID userId, UserUpdateRequest request);
    void deleteUser(UUID userId);
}
