package ai.ecma.appticket.service;

import ai.ecma.appticket.enums.PermissionEnum;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.RoleAttachDto;
import ai.ecma.appticket.payload.RoleReqDto;
import ai.ecma.appticket.payload.RoleResDto;

import java.util.Set;
import java.util.UUID;

public interface RoleService {
    ApiResult<RoleResDto> add(RoleReqDto roleReqDto);

    ApiResult<?> attachRole(RoleAttachDto roleAttachDto);

    ApiResult<?> getAll();

    ApiResult<RoleResDto> getPermissionsByRoleId(UUID id);

    ApiResult<Set<PermissionEnum>> getPermissions();

}
