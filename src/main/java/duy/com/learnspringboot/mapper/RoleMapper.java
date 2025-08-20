package duy.com.learnspringboot.mapper;

import duy.com.learnspringboot.dto.request.role.CreateRoleRequest;
import duy.com.learnspringboot.dto.response.role.RoleResponse;
import duy.com.learnspringboot.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(CreateRoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
