package duy.com.learnspringboot.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import duy.com.learnspringboot.dto.request.role.CreateRoleRequest;
import duy.com.learnspringboot.dto.response.ApiResponse;
import duy.com.learnspringboot.dto.response.role.RoleResponse;
import duy.com.learnspringboot.service.IRoleService;
import duy.com.learnspringboot.utils.SecurityExpressions;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/roles")
@PreAuthorize(SecurityExpressions.HAS_ROLE_ADMIN)
public class RoleController {
    IRoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> create(@RequestBody @Valid CreateRoleRequest createRoleRequest) {
        RoleResponse roleResponse = roleService.create(createRoleRequest);
        return ApiResponse.<RoleResponse>builder()
                .code(HttpStatus.CREATED.value())
                .data(roleResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> findAll() {
        List<RoleResponse> roleResponseList = roleService.findAll();
        return ApiResponse.<List<RoleResponse>>builder().data(roleResponseList).build();
    }

    @DeleteMapping("/{roleName}")
    public ApiResponse<Void> delete(@PathVariable String roleName) {
        roleService.delete(roleName);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Delete a permission successfully")
                .build();
    }
}
