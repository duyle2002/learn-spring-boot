package duy.com.learnspringboot.service;

import java.util.List;

import duy.com.learnspringboot.dto.request.role.CreateRoleRequest;
import duy.com.learnspringboot.dto.response.role.RoleResponse;

public interface IRoleService {
    RoleResponse create(CreateRoleRequest request);

    List<RoleResponse> findAll();

    void delete(String roleName);
}
