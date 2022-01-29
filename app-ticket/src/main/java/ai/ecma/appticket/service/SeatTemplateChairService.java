package ai.ecma.appticket.service;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.SeatTemplateChairResDto;
import ai.ecma.appticket.payload.seatTemplate.*;

import java.util.UUID;

public interface SeatTemplateChairService {
    ApiResult<SeatTemplateDto> getAllChairsBySeatTemplate(UUID seatTemplateId);

    ApiResult<SeatTemplateChairResDto> getOneChair(UUID id);

    ApiResult<SeatTemplateDto> addSection(SectionAddDto sectionAddDto);

    ApiResult<SeatTemplateDto> addRow(RowAddDto rowAddDto);

    ApiResult<SeatTemplateDto> addChair(ChairAddDTO chairAddDTO);

    ApiResult<SeatTemplateDto> editSection(SectionEditDto sectionEditDto);

    ApiResult<SeatTemplateDto> editRow(RowEditDto rowEditDto);

    ApiResult<SeatTemplateDto> editChair(ChairEditDto chairEditDto);

    ApiResult<?> deleteSection(SectionDeleteDto sectionDeleteDto);

    ApiResult<?> deleteRow(RowDeleteDto rowDeleteDto);

    ApiResult<?> deleteChair(UUID id);
}
