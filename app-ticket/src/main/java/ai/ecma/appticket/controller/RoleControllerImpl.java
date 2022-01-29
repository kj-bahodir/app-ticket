package ai.ecma.appticket.controller;

import ai.ecma.appticket.enums.PermissionEnum;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.RoleAttachDto;
import ai.ecma.appticket.payload.RoleReqDto;
import ai.ecma.appticket.payload.RoleResDto;
import ai.ecma.appticket.repository.RoleRepository;
import ai.ecma.appticket.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
public class RoleControllerImpl implements RoleController {
    private final RoleService roleService;

    public RoleControllerImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "Tadbirlarning listini sahifalab olish")
    @Override
    public ApiResult<RoleResDto> add(RoleReqDto roleReqDto) {
        return roleService.add(roleReqDto);
    }

    @Override
    public ApiResult<?> attachRole(RoleAttachDto roleAttachDto) {
        return roleService.attachRole(roleAttachDto);
    }

    @Override
    public ApiResult<?> getAll() {
        return roleService.getAll();
    }

    @Override
    public ApiResult<RoleResDto> getPermissionById(UUID id) {
        return roleService.getPermissionsByRoleId(id);
    }

    @Override
    public ApiResult<Set<PermissionEnum>> getAllPermission() {
        return roleService.getPermissions();
    }
}
