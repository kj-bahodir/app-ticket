package ai.ecma.appticket.service;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.EventReqDto;
import ai.ecma.appticket.payload.EventResDTO;

import java.util.UUID;

public interface EventService {
    ApiResult<CustomPage<EventResDTO>> getAll(int page, int size);

    ApiResult<EventResDTO> getOne(UUID id);

    ApiResult<EventResDTO> add(EventReqDto eventReqDto);

    ApiResult<EventResDTO> edit(EventReqDto eventReqDto, UUID id);

    ApiResult<?> delete(UUID id);
}
