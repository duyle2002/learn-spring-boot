package duy.com.learnspringboot.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import duy.com.learnspringboot.dto.request.role.CreateRoleRequest;
import duy.com.learnspringboot.dto.response.role.RoleResponse;
import duy.com.learnspringboot.entity.Permission;
import duy.com.learnspringboot.entity.Role;
import duy.com.learnspringboot.exception.BadRequestException;
import duy.com.learnspringboot.exception.ErrorCode;
import duy.com.learnspringboot.exception.ResourceNotFoundException;
import duy.com.learnspringboot.mapper.RoleMapper;
import duy.com.learnspringboot.repository.PermissionRepository;
import duy.com.learnspringboot.repository.RoleRepository;
import duy.com.learnspringboot.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements IRoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    RoleMapper roleMapper;

    @Override
    public RoleResponse create(CreateRoleRequest request) {
        var existingRole = roleRepository.findById(request.getName());
        if (existingRole.isPresent()) {
            throw new BadRequestException(ErrorCode.ROLE_ALREADY_EXISTS);
        }

        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());

        Role role = roleMapper.toRole(request);
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> findAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @Override
    public void delete(String roleName) {
        var role = roleRepository
                .findById(roleName)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ROLE_NOT_FOUND));

        roleRepository.deleteById(roleName);
    }
}
