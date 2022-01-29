package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.SeatTemplateReqDto;
import ai.ecma.appticket.payload.SeatTemplateResDto;
import ai.ecma.appticket.service.SeatTemplateService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SeatTemplateControllerImpl implements SeatTemplateController  {
    private final SeatTemplateService seatTemplateService;

    public SeatTemplateControllerImpl(SeatTemplateService seatTemplateService) {
        this.seatTemplateService = seatTemplateService;
    }


    @PreAuthorize(value = "hasAuthority('VIEW_SEAT_TEMPLATE')")
    @Override
    public ApiResult<CustomPage<SeatTemplateResDto>> getSeatTemplateList(int page, int size) {
        return seatTemplateService.getSeatTemplateList(page,size);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_SEAT_TEMPLATE')")
    @Override
    public ApiResult<SeatTemplateResDto> getSeatTemplateById(UUID id) {
        return seatTemplateService.getSeatTemplateById(id);
    }

    @PreAuthorize(value = "hasAuthority('ADD_SEAT_TEMPLATE')")
    @Override
    public ApiResult<SeatTemplateResDto> addSeatTemplate(SeatTemplateReqDto seatTemplateReqDto) {
        return seatTemplateService.addSeatTemplate(seatTemplateReqDto);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_SEAT_TEMPLATE')")
    @Override
    public ApiResult<SeatTemplateResDto> editSeatTemplate(SeatTemplateReqDto seatTemplateReqDto, UUID id) {
        return seatTemplateService.editSeatTemplate(seatTemplateReqDto,id);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_SEAT_TEMPLATE')")
    @Override
    public ApiResult<?> deleteSeatTemplateById(UUID id) {
        return seatTemplateService.deleteSeatTemplateById(id);
    }

}
