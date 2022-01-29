package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.SpeakerReqDto;
import ai.ecma.appticket.payload.SpeakerResDto;
import ai.ecma.appticket.service.SpeakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SpeakerControllerImpl implements SpeakerController {

    private final SpeakerService speakerService;

    @Override
    public ApiResult<CustomPage<SpeakerResDto>> getAll(int page, int size) {
        return speakerService.getAll(page , size);
    }


    @Override
    public ApiResult<SpeakerResDto> getOne(UUID id) {
        return speakerService.getOne(id);
    }

    @PreAuthorize(value = "hasAuthority('ADD_SPEAKER')")
    @Override
    public ApiResult<SpeakerResDto> add(SpeakerReqDto speakerDto) {
        return speakerService.add(speakerDto);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_SPEAKER')")
    @Override
    public ApiResult<SpeakerResDto> edit(SpeakerReqDto speakerDto, UUID id) {
        return speakerService.edit(speakerDto,id);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_SPEAKER')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return speakerService.delete(id);
    }
}
