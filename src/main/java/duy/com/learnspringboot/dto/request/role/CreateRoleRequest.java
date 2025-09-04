package duy.com.learnspringboot.dto.request.role;

import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateRoleRequest {
    String name;
    String description;
    Set<String> permissions;
}
