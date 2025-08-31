package duy.com.learnspringboot.dto.request.user;

import duy.com.learnspringboot.validator.DateOfBirthConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String firstName;
    String lastName;

    @Size(min = 4, message = "User name must be at least 4 characters")
    String username;

    @Size(min = 8, message = "Password must be at least 8 characters")
    String password;

    @DateOfBirthConstraint(min = 18)
    LocalDate dateOfBirth;
}
