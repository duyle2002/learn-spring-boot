package duy.com.learnspringboot.service;

import duy.com.learnspringboot.dto.request.user.UserRequestDTO;
import duy.com.learnspringboot.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserRequestDTO getUserById(int userId) {
        throw new ResourceNotFoundException("User not found");
    }
}
