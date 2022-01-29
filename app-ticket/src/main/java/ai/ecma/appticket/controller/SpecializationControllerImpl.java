package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.SpecializationDto;
import ai.ecma.appticket.payload.SpecializationReqDto;
import ai.ecma.appticket.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SpecializationControllerImpl implements SpecializationController {

    private final SpecializationService specializationService;

    @Override
    public ApiResult<CustomPage<SpecializationDto>> getAll(int page, int size) {
        return specializationService.getAll(page, size);
    }

    @Override
    public ApiResult<SpecializationDto> getOne(UUID uuid) {
        return specializationService.getOne(uuid);
    }


    @PreAuthorize(value = "hasAuthority('ADD_SPEAKER')")
    @Override
    public ApiResult<SpecializationDto> add(@Valid SpecializationReqDto specializationReqDto) {
        return specializationService.add(specializationReqDto);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_SPEAKER')")
    @Override
    public ApiResult<SpecializationDto> edit(@Valid SpecializationReqDto specializationReqDto, UUID uuid) {
        return specializationService.edit(specializationReqDto, uuid);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_SPEAKER')")
    @Override
    public ApiResult<?> delete(UUID uuid) {
        return specializationService.delete(uuid);
    }
}
