package duy.com.learnspringboot.service;

import duy.com.learnspringboot.dto.request.user.UserCreationRequest;
import duy.com.learnspringboot.dto.request.user.UserUpdateRequest;
import duy.com.learnspringboot.dto.response.user.UserResponse;
import duy.com.learnspringboot.entity.User;
import duy.com.learnspringboot.enums.Role;
import duy.com.learnspringboot.exception.BadRequestException;
import duy.com.learnspringboot.exception.ErrorCode;
import duy.com.learnspringboot.exception.ResourceNotFoundException;
import duy.com.learnspringboot.mapper.UserMapper;
import duy.com.learnspringboot.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserServiceImpl implements IUserService {
    UserRepository userRepository;
    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        boolean exisingUserByUserName = userRepository.existsByUsername(request.getUsername().toLowerCase());

        if (exisingUserByUserName) {
            throw new BadRequestException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = this.userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        User createdUser = this.userRepository.save(user);
        return this.userMapper.toUserResponse(createdUser);
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
        User existingUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));

        this.userMapper.updateUser(existingUser, request);

        existingUser = this.userRepository.save(existingUser);
        return this.userMapper.toUserResponse(existingUser);
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
