package duy.com.learnspringboot.dto.response.user;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import duy.com.learnspringboot.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    UUID id;
    String username;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    Set<Role> roles;
}
