package duy.com.learnspringboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import duy.com.learnspringboot.dto.request.permission.CreatePermissionRequest;
import duy.com.learnspringboot.dto.response.permission.PermissionResponse;
import duy.com.learnspringboot.entity.Permission;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionMapper {
    Permission toPermission(CreatePermissionRequest createPermissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);
}
