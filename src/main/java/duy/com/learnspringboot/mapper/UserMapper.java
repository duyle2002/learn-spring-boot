package duy.com.learnspringboot.mapper;

import duy.com.learnspringboot.dto.request.user.UserCreationRequest;
import duy.com.learnspringboot.dto.request.user.UserUpdateRequest;
import duy.com.learnspringboot.dto.response.user.UserResponse;
import duy.com.learnspringboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);

    UserResponse toUserResponse(User user);
}
