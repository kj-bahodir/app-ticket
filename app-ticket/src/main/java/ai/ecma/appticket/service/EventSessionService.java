package ai.ecma.appticket.service;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.EventSessionReqDto;
import ai.ecma.appticket.payload.EventSessionResDto;

import java.util.List;
import java.util.UUID;

public interface EventSessionService {

    ApiResult<CustomPage<EventSessionResDto>> getAll(int page, int size);

    ApiResult<EventSessionResDto> getOne(UUID id);

    ApiResult<List<EventSessionResDto>> getByEventId(UUID eventId);

    ApiResult<EventSessionResDto> add(EventSessionReqDto eventSessionReqDto);

    ApiResult<EventSessionResDto> edit(EventSessionReqDto eventSessionReqDto, UUID id);

    ApiResult<?> delete(UUID id);
}
