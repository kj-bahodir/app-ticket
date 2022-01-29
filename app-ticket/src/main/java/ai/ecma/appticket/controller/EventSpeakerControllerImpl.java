package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.EventSpeakerReqDto;
import ai.ecma.appticket.payload.EventSpeakerResDto;
import ai.ecma.appticket.payload.EventTypeReqDto;
import ai.ecma.appticket.service.EventSpeakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EventSpeakerControllerImpl implements EventSpeakerController{

    private final EventSpeakerService eventSpeakerService;

    @Override
    public ApiResult<List<EventSpeakerResDto>> getAll() {
        return eventSpeakerService.getAll();
    }

    @Override
    public ApiResult<EventSpeakerResDto> getOne(UUID id) {
        return eventSpeakerService.getOne(id);
    }


    @PreAuthorize(value = "hasAuthority('ADD_EVENT')")
    @Override
    public ApiResult<EventSpeakerResDto> add(EventSpeakerReqDto eventSpeakerReqDto) {
        return eventSpeakerService.add(eventSpeakerReqDto);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_EVENT')")
    @Override
    public ApiResult<EventSpeakerResDto> edit(EventSpeakerReqDto eventSpeakerReqDto, UUID id) {
        return eventSpeakerService.edit(eventSpeakerReqDto, id);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_EVENT')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return eventSpeakerService.delete(id);
    }
}
