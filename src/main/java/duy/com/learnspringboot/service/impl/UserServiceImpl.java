package duy.com.learnspringboot.service.impl;

import duy.com.learnspringboot.dto.request.user.UserCreationRequest;
import duy.com.learnspringboot.dto.request.user.UserUpdateRequest;
import duy.com.learnspringboot.dto.response.user.UserResponse;
import duy.com.learnspringboot.entity.Role;
import duy.com.learnspringboot.entity.User;
import duy.com.learnspringboot.exception.BadRequestException;
import duy.com.learnspringboot.exception.ErrorCode;
import duy.com.learnspringboot.exception.ResourceNotFoundException;
import duy.com.learnspringboot.mapper.UserMapper;
import duy.com.learnspringboot.repository.RoleRepository;
import duy.com.learnspringboot.repository.UserRepository;
import duy.com.learnspringboot.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserServiceImpl implements IUserService {
    UserRepository userRepository;
    RoleRepository roleRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        String username = request.getUsername().toLowerCase();
        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = roleRepository.findById("USER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("USER").build()));

        user.setRoles(Set.of(userRole));

        User createdUser = userRepository.save(user);
        return userMapper.toUserResponse(createdUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return this.userRepository.findAll().stream().map(this.userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse getUserById(UUID userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
        return this.userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(UUID userId, UserUpdateRequest request) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(existingUser, request);

        Set<Role> updatedRoles = new HashSet<>(roleRepository.findAllById(request.getRoles()));
        existingUser.setRoles(updatedRoles);

        User savedUser = userRepository.save(existingUser);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public void deleteUser(UUID userId) {
        User existingUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
        this.userRepository.delete(existingUser);
    }

    @Override
    public UserResponse getMyInfo() {
        var securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));

        return this.userMapper.toUserResponse(user);
    }
}
