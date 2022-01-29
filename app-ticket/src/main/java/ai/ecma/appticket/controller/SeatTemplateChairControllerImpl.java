package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.SeatTemplateChairResDto;
import ai.ecma.appticket.payload.seatTemplate.*;
import ai.ecma.appticket.service.SeatTemplateChairService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SeatTemplateChairControllerImpl implements SeatTemplateChairController {

    private final SeatTemplateChairService seatTemplateChairService;


    public SeatTemplateChairControllerImpl(SeatTemplateChairService seatTemplateChairService) {
        this.seatTemplateChairService = seatTemplateChairService;
    }

    @PreAuthorize(value = "hasAuthority('VIEW_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<SeatTemplateDto> getAllChairsBySeatTemplate(UUID seatTemplateId) {
        return seatTemplateChairService.getAllChairsBySeatTemplate(seatTemplateId);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<SeatTemplateChairResDto> getOneChair(UUID id) {
        return seatTemplateChairService.getOneChair(id);
    }


    @PreAuthorize(value = "hasAuthority('ADD_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<SeatTemplateDto> addSection(SectionAddDto sectionAddDto) {
        return seatTemplateChairService.addSection(sectionAddDto);
    }

    @PreAuthorize(value = "hasAuthority('ADD_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<SeatTemplateDto> addRow(RowAddDto rowAddDto) {
        return seatTemplateChairService.addRow(rowAddDto);
    }

    @PreAuthorize(value = "hasAuthority('ADD_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<SeatTemplateDto> addChair(ChairAddDTO chairAddDTO) {
        return seatTemplateChairService.addChair(chairAddDTO);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<SeatTemplateDto> editSection(SectionEditDto sectionEditDto) {
        return seatTemplateChairService.editSection(sectionEditDto);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<SeatTemplateDto> editRow(RowEditDto rowEditDto) {
        return seatTemplateChairService.editRow(rowEditDto);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<SeatTemplateDto> editChair(ChairEditDto chairEditDto) {
        return seatTemplateChairService.editChair(chairEditDto);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<?> deleteSection(SectionDeleteDto sectionDeleteDto) {
        return seatTemplateChairService.deleteSection(sectionDeleteDto);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<?> deleteRow(RowDeleteDto rowDeleteDto) {
        return seatTemplateChairService.deleteRow(rowDeleteDto);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<?> deleteChair(UUID id) {
        return seatTemplateChairService.deleteChair(id);
    }
}
