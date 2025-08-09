package duy.com.learnspringboot.service;

import duy.com.learnspringboot.dto.request.user.UserCreationRequest;
import duy.com.learnspringboot.dto.request.user.UserRequestDTO;
import duy.com.learnspringboot.dto.request.user.UserUpdateRequest;
import duy.com.learnspringboot.entity.User;
import duy.com.learnspringboot.exception.ResourceNotFoundException;
import duy.com.learnspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRequestDTO getUserById(int userId) {
        throw new ResourceNotFoundException("User not found");
    }

    public User createUser(UserCreationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());

        return this.userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User getUserById(UUID userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(UUID userId, UserUpdateRequest request) {
        User existingUser = this.getUserById(userId);

        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setDateOfBirth(request.getDateOfBirth());
        existingUser.setPassword(request.getPassword());

        return this.userRepository.save(existingUser);
    }

    public void deleteUser(UUID userId) {
        User existingUser = this.getUserById(userId);
        this.userRepository.delete(existingUser);
    }
}
