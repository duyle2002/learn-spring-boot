package duy.com.learnspringboot.dto.response.role;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import duy.com.learnspringboot.entity.Permission;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleResponse {
    String name;
    String description;

    Set<Permission> permissions;
}
