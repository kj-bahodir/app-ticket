package ai.ecma.appticket.service;

import ai.ecma.appticket.entity.Role;
import ai.ecma.appticket.entity.User;
import ai.ecma.appticket.enums.PermissionEnum;
import ai.ecma.appticket.exception.RestException;
import ai.ecma.appticket.mapper.CustomMapper;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.RoleAttachDto;
import ai.ecma.appticket.payload.RoleReqDto;
import ai.ecma.appticket.payload.RoleResDto;
import ai.ecma.appticket.repository.RoleRepository;
import ai.ecma.appticket.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ApiResult<RoleResDto> add(RoleReqDto roleReqDto) {
        if (roleRepository.existsByName(roleReqDto.getName()))
            throw new RestException(HttpStatus.CONFLICT, "Bunday role nomi takrorlangan!");

        Role role = new Role(
                roleReqDto.getName(),
                roleReqDto.getDescription(),
                roleReqDto.getPermissions()
        );
        roleRepository.save(role);
        return ApiResult.successResponse(CustomMapper.roleToDto(role));
    }

    @Override
    public ApiResult<?> attachRole(RoleAttachDto roleAttachDto) {
        Role role = roleRepository.findById(roleAttachDto.getRoleId()).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "Role topilmadi!"));
        List<User> userList = userRepository.findAllById(roleAttachDto.getUserIds());
        if (userList.size() != roleAttachDto.getUserIds().size())
            throw new RestException(HttpStatus.CONFLICT, "Siz xato qilyapsiz!");

        for (User user : userList) {
            user.setRole(role);
        }
        userRepository.saveAll(userList);
        return ApiResult.successResponse("Hammasi bajarildi,hurmatiz borakan");
    }

    @Override
    public ApiResult<?> getAll() {
        return ApiResult.successResponse(roleRepository.findAll().stream().map(CustomMapper::roleToDto).collect(Collectors.toList()));
    }

    @Override
    public ApiResult<RoleResDto> getPermissionsByRoleId(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Role topilmadi!"));
        return ApiResult.successResponse(CustomMapper.roleToDto(role));
    }

    @Override
    public ApiResult<Set<PermissionEnum>> getPermissions() {
        PermissionEnum[] values = PermissionEnum.values();
        return ApiResult.successResponse(CustomMapper.permissionToSet(values));
    }


}
