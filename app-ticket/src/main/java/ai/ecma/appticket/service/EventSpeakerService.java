package ai.ecma.appticket.service;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.EventSpeakerReqDto;
import ai.ecma.appticket.payload.EventSpeakerResDto;

import java.util.List;
import java.util.UUID;

public interface EventSpeakerService {
    ApiResult<List<EventSpeakerResDto>> getAll();

    ApiResult<EventSpeakerResDto> getOne(UUID id);

    ApiResult<EventSpeakerResDto> add(EventSpeakerReqDto eventSpeakerReqDto);

    ApiResult<EventSpeakerResDto> edit(EventSpeakerReqDto eventSpeakerReqDto, UUID id);

    ApiResult<?> delete(UUID id);
}
