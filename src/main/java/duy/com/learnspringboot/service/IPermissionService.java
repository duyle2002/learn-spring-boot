package duy.com.learnspringboot.service;

import java.util.List;

import duy.com.learnspringboot.dto.request.permission.CreatePermissionRequest;
import duy.com.learnspringboot.dto.response.permission.PermissionResponse;

public interface IPermissionService {
    PermissionResponse create(CreatePermissionRequest request);

    List<PermissionResponse> findAll();

    void delete(String permissionName);
}
