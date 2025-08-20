package duy.com.learnspringboot.controller;

import duy.com.learnspringboot.dto.request.permission.CreatePermissionRequest;
import duy.com.learnspringboot.dto.response.ApiResponse;
import duy.com.learnspringboot.dto.response.permission.PermissionResponse;
import duy.com.learnspringboot.service.IPermissionService;
import duy.com.learnspringboot.utils.SecurityExpressions;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/permissions")
@PreAuthorize(SecurityExpressions.HAS_ROLE_ADMIN)
public class PermissionController {
    IPermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionResponse> create(@RequestBody @Valid CreatePermissionRequest createPermissionRequest){
        PermissionResponse permissionResponse = permissionService.create(createPermissionRequest);
        return ApiResponse.<PermissionResponse>builder()
                .code(HttpStatus.CREATED.value())
                .data(permissionResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> findAll(){
        List<PermissionResponse> permissionResponseList = permissionService.findAll();
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(HttpStatus.OK.value())
                .data(permissionResponseList)
                .build();
    }

    @DeleteMapping("/{permissionName}")
    public ApiResponse<Void> delete(@PathVariable String permissionName){
        permissionService.delete(permissionName);

        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Delete a permission successfully")
                .build();
    }
}
