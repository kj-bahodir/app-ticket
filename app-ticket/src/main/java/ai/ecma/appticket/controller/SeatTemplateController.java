package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.SeatTemplateReqDto;
import ai.ecma.appticket.payload.SeatTemplateResDto;
import ai.ecma.appticket.utils.AppConstant;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping(RestConstant.SEAT_TEMPLATE_CONTROLLER)
@Tag(name = "SeatTemplate operatsiyalari", description = "SeatTemplate")
public interface SeatTemplateController {

    @Operation(summary = "SeatTemplatelarning listini sahifalab olish")
    @GetMapping
    ApiResult<CustomPage<SeatTemplateResDto>> getSeatTemplateList(@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) int page,
                                                                  @RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SIZE) int size);

    @Operation(summary = "Bitta seatTemplateni olish")
    @GetMapping("/{id}")
    ApiResult<SeatTemplateResDto> getSeatTemplateById(@PathVariable UUID id);

    @Operation(summary = "SeatTemplate qo'shish")
    @PostMapping
    ApiResult<SeatTemplateResDto> addSeatTemplate(@RequestBody @Valid SeatTemplateReqDto seatTemplateReqDto);

    @Operation(summary = "SeatTemplate edit qilish")
    @PutMapping("/{id}")
    ApiResult<SeatTemplateResDto> editSeatTemplate(@RequestBody @Valid SeatTemplateReqDto seatTemplateReqDto, @PathVariable UUID id);

    @Operation(summary = "SeatTemplateni o'chirish")
    @DeleteMapping("/{id}")
    ApiResult<?> deleteSeatTemplateById(@PathVariable UUID id);
}
