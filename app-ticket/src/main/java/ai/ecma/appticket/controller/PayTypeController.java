package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.PayTypeResDto;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(RestConstant.PAY_TYPE_CONTROLLER)
@Tag(name = "To'lov turi operatsiyalari",description = "To'lov turi")
public interface PayTypeController {

    @Operation(summary = "To'lov turlarini listini olish")
    @GetMapping
    ApiResult<List<PayTypeResDto>> getAll();

    @Operation(summary = "Bitta to'lov turini olish")
    @GetMapping("/{id}")
    ApiResult<PayTypeResDto> get(@PathVariable UUID id);



    //kiruvchi dto PayTypeReqDto bo'lishi kerak edi menimcha
    @Operation(summary = "To'lov turini qo'shish (sevice yozilmagan)")
    @PostMapping
    ApiResult<PayTypeResDto> create(@RequestBody @Valid PayTypeResDto payTypeResDto);

    @Operation(summary = "To'lov turini edit qilish (sevice yozilmagan)")
    @PutMapping("/{id}")
    ApiResult<PayTypeResDto> edit(@RequestBody @Valid PayTypeResDto payTypeResDto, @PathVariable UUID id);

    @Operation(summary = "To'lov turini o'chirish (sevice yozilmagan)")
    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);
}
