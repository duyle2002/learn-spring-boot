package duy.com.learnspringboot.service.impl;

import duy.com.learnspringboot.dto.request.permission.CreatePermissionRequest;
import duy.com.learnspringboot.dto.response.permission.PermissionResponse;
import duy.com.learnspringboot.entity.Permission;
import duy.com.learnspringboot.exception.BadRequestException;
import duy.com.learnspringboot.exception.ErrorCode;
import duy.com.learnspringboot.exception.ResourceNotFoundException;
import duy.com.learnspringboot.mapper.PermissionMapper;
import duy.com.learnspringboot.repository.PermissionRepository;
import duy.com.learnspringboot.service.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements IPermissionService {
    PermissionRepository permissionRepository;

    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(CreatePermissionRequest request) {
        var existingPermission = permissionRepository.findById(request.getName());
        if (existingPermission.isPresent()) {
            throw new BadRequestException(ErrorCode.PERMISSION_ALREADY_EXISTS);
        }

        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> findAll() {
        return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @Override
    public void delete(String permissionName) {
        var permission = permissionRepository.findById(permissionName)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PERMISSION_NOT_FOUND));
        permissionRepository.deleteById(permission.getName());
    }
}
