package duy.com.learnspringboot.controller;

import duy.com.learnspringboot.dto.request.user.UserRequestDTO;
import duy.com.learnspringboot.dto.response.ResponseData;
import duy.com.learnspringboot.dto.response.ResponseError;
import duy.com.learnspringboot.service.UserService;
import duy.com.learnspringboot.utils.UserStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseData<Integer> addUser(@Valid @RequestBody UserRequestDTO userRequest) {
//        return new ResponseData<>(HttpStatus.CREATED.value(), "Added a new user successfully", 1);
        return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), "Can not create user");
    }

    @PutMapping("/{userId}")
    public ResponseData<?> updateUser(@PathVariable String userId, @RequestBody UserRequestDTO userRequest) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Updated a user successfully");
    }

    @PatchMapping("/{userId}")
    public ResponseData<?> updatePatchUser(@PathVariable String userId, @RequestParam boolean status) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Patch updated a user successfully");
    }

    @DeleteMapping("/{userId}")
    public ResponseData<?> deleteUser(@PathVariable String userId) {
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "User deleted successfully");
    }

    @GetMapping("/{userId}")
    public ResponseData<UserRequestDTO> getUser(@PathVariable @Min(1) int userId) {
//        return new ResponseData<>(HttpStatus.OK.value(), "User get successfully", new UserRequestDTO(
//                "John", "Doe", "email@gmail.com", "1234567890", new Date(), UserStatus.ACTIVE
//        ));
        UserRequestDTO user = userService.getUserById(userId);
        return new ResponseData<>(HttpStatus.OK.value(), "User successfully retrieved", user);
    }

    @GetMapping("/")
    public ResponseData<List<UserRequestDTO>> list(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam String email
    ) {
        List<UserRequestDTO> list = List.of(
                new UserRequestDTO("John", "Doe", email, "1234567890", new Date(), UserStatus.ACTIVE),
                new UserRequestDTO("Jane", "Doe", email, "0987654321", new Date(), UserStatus.INACTIVE)
        );
        return new ResponseData<>(HttpStatus.OK.value(), "User list successfully", list);
    }
}
