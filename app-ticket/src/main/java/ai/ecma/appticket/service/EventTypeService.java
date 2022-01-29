package ai.ecma.appticket.service;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.EventTypeResDto;
import ai.ecma.appticket.payload.EventTypeReqDto;

import java.util.List;
import java.util.UUID;

public interface EventTypeService {
    ApiResult<List<EventTypeResDto>> getAll();

    ApiResult<EventTypeResDto> get(UUID id);

    ApiResult<EventTypeResDto> add(EventTypeReqDto eventTypeReqDto);

    ApiResult<EventTypeResDto> edit(EventTypeReqDto eventTypeReqDto, UUID id);

    ApiResult<?> delete(UUID id);
}
