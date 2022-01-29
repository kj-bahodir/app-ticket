package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.EventReqDto;
import ai.ecma.appticket.payload.EventResDTO;
import ai.ecma.appticket.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EventControllerImpl implements EventController{

    private final EventService eventService;

    @Override
    public ApiResult<CustomPage<EventResDTO>> getAll(int page, int size) {
        return eventService.getAll(page, size);
    }

    @Override
    public ApiResult<EventResDTO> getOne(UUID id) {
        return eventService.getOne(id);
    }

    @PreAuthorize(value = "hasAuthority('ADD_EVENT')")
    @Override
    public ApiResult<EventResDTO> add(EventReqDto eventReqDto) {
        return eventService.add(eventReqDto);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_EVENT')")
    @Override
    public ApiResult<EventResDTO> edit(EventReqDto eventReqDto, UUID id) {
        return eventService.edit(eventReqDto, id);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_EVENT')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return eventService.delete(id);
    }
}
