package ai.ecma.appticket.service;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.SpeakerResDto;
import ai.ecma.appticket.payload.SpeakerReqDto;

import java.util.UUID;

public interface SpeakerService {

    ApiResult<CustomPage<SpeakerResDto>> getAll(int page, int size);

    ApiResult<SpeakerResDto> getOne(UUID id);

    ApiResult<SpeakerResDto> add(SpeakerReqDto speakerDto);

    ApiResult<SpeakerResDto> edit(SpeakerReqDto speakerDto, UUID id);

    ApiResult<?> delete(UUID id);
}
