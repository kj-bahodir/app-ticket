package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.EventTypeReqDto;
import ai.ecma.appticket.payload.EventTypeResDto;
import ai.ecma.appticket.service.EventTypeService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class EventTypeControllerImp implements EventTypeController{
    private final EventTypeService eventTypeService;

    public EventTypeControllerImp( EventTypeService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }

    @Override
    public ApiResult<List<EventTypeResDto>> getAll() {
        return eventTypeService.getAll();
    }

    @Override
    public ApiResult<EventTypeResDto> get(UUID id) {
        return eventTypeService.get(id);
    }

    @PreAuthorize(value = "hasAuthority('ADD_EVENT')")
    @Override
    public ApiResult<EventTypeResDto> add(EventTypeReqDto eventTypeReqDto) {
        return eventTypeService.add(eventTypeReqDto);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_EVENT')")
    @Override
    public ApiResult<EventTypeResDto> edit(EventTypeReqDto eventTypeReqDto, UUID id) {
        return eventTypeService.edit(eventTypeReqDto, id);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_EVENT')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return eventTypeService.delete(id);
    }
}
