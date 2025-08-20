package duy.com.learnspringboot.service;

import duy.com.learnspringboot.dto.request.permission.CreatePermissionRequest;
import duy.com.learnspringboot.dto.response.permission.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    PermissionResponse create(CreatePermissionRequest request);
    List<PermissionResponse> findAll();
    void delete(String permissionName);
}
