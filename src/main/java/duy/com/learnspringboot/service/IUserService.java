package duy.com.learnspringboot.service;

import java.util.List;
import java.util.UUID;

import duy.com.learnspringboot.dto.request.user.UserCreationRequest;
import duy.com.learnspringboot.dto.request.user.UserUpdateRequest;
import duy.com.learnspringboot.dto.response.user.UserResponse;

public interface IUserService {
    UserResponse createUser(UserCreationRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(UUID userId);

    UserResponse updateUser(UUID userId, UserUpdateRequest request);

    void deleteUser(UUID userId);

    UserResponse getMyInfo();
}
