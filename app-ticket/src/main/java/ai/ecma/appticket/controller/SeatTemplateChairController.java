package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.SeatTemplateChairResDto;
import ai.ecma.appticket.payload.seatTemplate.*;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping(RestConstant.SEAT_TEMPLATE_CHAIR_CONTROLLER)
@Tag(name = "SeatTemplateChair operatsiyalari",description = "SeatTemplateChair")
public interface SeatTemplateChairController {

    @Operation(summary = "---------------------")
    @GetMapping("/template/{seatTemplateId}")
    ApiResult<SeatTemplateDto> getAllChairsBySeatTemplate(@PathVariable UUID seatTemplateId);

    @Operation(summary = "Bitta chairni olish")
    @GetMapping("/chair/{id}")
    ApiResult<SeatTemplateChairResDto> getOneChair(@PathVariable UUID id);

    @Operation(summary = "Seksiya qo'shish")
    @PostMapping("/addSection")
    ApiResult<SeatTemplateDto> addSection(@RequestBody @Valid SectionAddDto sectionAddDto);

    @Operation(summary = "Qator qo'shish")
    @PostMapping("/addRow")
    ApiResult<SeatTemplateDto> addRow(@RequestBody @Valid RowAddDto rowAddDto);

    @Operation(summary = "Chair qo'shish")
    @PostMapping("/addChair")
    ApiResult<SeatTemplateDto> addChair(@RequestBody @Valid ChairAddDTO chairAddDTO);

    @Operation(summary = "Seksiyani edit qilish")
    @PutMapping("/editSection")
    ApiResult<SeatTemplateDto> editSection(@RequestBody @Valid SectionEditDto sectionEditDto);

    @Operation(summary = "Qatorni edit qilish")
    @PutMapping("/editRow")
    ApiResult<SeatTemplateDto> editRow(@RequestBody @Valid RowEditDto rowEditDto);

    @Operation(summary = "Chairni edit qilish")
    @PutMapping("/editChair")
    ApiResult<SeatTemplateDto> editChair(@RequestBody @Valid ChairEditDto chairEditDto);

    @Operation(summary = "Seksiyani o'chirish")
    @DeleteMapping("/deleteSection")
    ApiResult<?> deleteSection(@RequestBody @Valid SectionDeleteDto sectionDeleteDto);

    @Operation(summary = "Qatorni o'chirish")
    @DeleteMapping("/deleteRow")
    ApiResult<?> deleteRow(@RequestBody @Valid RowDeleteDto rowDeleteDto);

    @Operation(summary = "Chairni o'chirish")
    @DeleteMapping("/deleteChair/{id}")
    ApiResult<?> deleteChair(@PathVariable UUID id);
}
