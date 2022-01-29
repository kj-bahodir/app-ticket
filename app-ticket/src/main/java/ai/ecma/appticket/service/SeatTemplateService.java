package ai.ecma.appticket.service;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.SeatTemplateReqDto;
import ai.ecma.appticket.payload.SeatTemplateResDto;

import java.util.UUID;

public interface SeatTemplateService {

    ApiResult<CustomPage<SeatTemplateResDto>> getSeatTemplateList(int page, int size);

    ApiResult<SeatTemplateResDto> getSeatTemplateById(UUID uuid);

    ApiResult<SeatTemplateResDto> addSeatTemplate(SeatTemplateReqDto seatTemplateReqDto);

    ApiResult<SeatTemplateResDto> editSeatTemplate(SeatTemplateReqDto seatTemplateReqDto, UUID id);

    ApiResult<?> deleteSeatTemplateById(UUID id);
}
