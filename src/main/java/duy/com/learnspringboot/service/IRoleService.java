package duy.com.learnspringboot.service;

import duy.com.learnspringboot.dto.request.role.CreateRoleRequest;
import duy.com.learnspringboot.dto.response.role.RoleResponse;

import java.util.List;

public interface IRoleService {
    RoleResponse create(CreateRoleRequest request);
    List<RoleResponse> findAll();
    void delete(String roleName);
}
