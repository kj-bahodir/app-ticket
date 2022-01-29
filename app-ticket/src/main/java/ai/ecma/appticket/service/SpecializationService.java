package ai.ecma.appticket.service;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.SpecializationDto;
import ai.ecma.appticket.payload.SpecializationReqDto;

import java.util.UUID;

public interface SpecializationService {
    ApiResult<CustomPage<SpecializationDto>> getAll(int page, int size);

    ApiResult<SpecializationDto> getOne(UUID id);

    ApiResult<SpecializationDto> add(SpecializationReqDto specializationDto);

    ApiResult<SpecializationDto> edit(SpecializationReqDto specializationReqDto, UUID uuid);

    ApiResult<?> delete(UUID uuid);

}
