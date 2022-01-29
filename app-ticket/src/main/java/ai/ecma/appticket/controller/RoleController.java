package ai.ecma.appticket.controller;

import ai.ecma.appticket.enums.PermissionEnum;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.RoleAttachDto;
import ai.ecma.appticket.payload.RoleReqDto;
import ai.ecma.appticket.payload.RoleResDto;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;

@RequestMapping(RestConstant.ROLE_CONTROLLER)
@Tag(name = "Role operatsiyalari", description = "Role")
public interface RoleController {

    String ATTACH_ROLE = "/attach-role";

    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @Operation(summary = "Role qo'shish")
    @PostMapping
    ApiResult<RoleResDto> add(@RequestBody @Valid RoleReqDto roleReqDto);


    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @Operation(summary = "Huquqlarni rolga biriktirish")
    @PostMapping(ATTACH_ROLE)
    ApiResult<?> attachRole(@RequestBody @Valid RoleAttachDto roleAttachDto);

    @PreAuthorize(value = "hasAuthority('VIEW_ROLE')")
    @Operation(summary = "Rolelarni listini olish")
    @GetMapping
    ApiResult<?> getAll();

    @PreAuthorize(value = "hasAuthority('VIEW_ROLE')")
    @Operation(summary = "Role id orqali huquqlarni olish")
    @GetMapping("/role-id/{id}")
    ApiResult<RoleResDto> getPermissionById(@PathVariable UUID id);

    @PreAuthorize(value = "hasAuthority('VIEW_ROLE')")
    @Operation(summary = "Huquqlarning listini olish")
    @GetMapping("/permissions")
    ApiResult<Set<PermissionEnum>> getAllPermission();
}
