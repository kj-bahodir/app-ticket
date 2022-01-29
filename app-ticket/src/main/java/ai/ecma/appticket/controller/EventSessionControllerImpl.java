package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.EventSessionReqDto;
import ai.ecma.appticket.payload.EventSessionResDto;
import ai.ecma.appticket.service.EventSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EventSessionControllerImpl implements EventSessionController{

    private final EventSessionService eventSessionService;

    @Override
    public ApiResult<CustomPage<EventSessionResDto>> getAll(int page, int size) {
        return eventSessionService.getAll(page, size);
    }

    @Override
    public ApiResult<EventSessionResDto> getOne(UUID id) {
        return eventSessionService.getOne(id);
    }

    @Override
    public ApiResult<List<EventSessionResDto>> getByEventId(UUID eventId) {
        return eventSessionService.getByEventId(eventId);
    }

    @PreAuthorize(value = "hasAuthority('ADD_EVENT_SESSION')")
    @Override
    public ApiResult<EventSessionResDto> add(EventSessionReqDto eventSessionReqDto) {
        return eventSessionService.add(eventSessionReqDto);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_EVENT_SESSION')")
    @Override
    public ApiResult<EventSessionResDto> edit(EventSessionReqDto eventSessionReqDto, UUID id) {
        return eventSessionService.edit(eventSessionReqDto, id);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_EVENT_SESSION')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return eventSessionService.delete(id);
    }
}
