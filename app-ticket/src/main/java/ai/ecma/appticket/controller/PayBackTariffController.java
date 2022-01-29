package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.PayBackTariffDto;
import ai.ecma.appticket.payload.PayBackTariffReqDto;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static ai.ecma.appticket.utils.AppConstant.DEFAULT_PAGE_NUMBER;
import static ai.ecma.appticket.utils.AppConstant.DEFAULT_PAGE_SIZE;

@RequestMapping(RestConstant.PAY_BACK_TARIFF_CONTROLLER)
@Tag(name = "Pul qaytarish tariffi operatsiyalari",description = "Pul qaytarish tariffi")
public interface PayBackTariffController {

    @Operation(summary = "Pul qaytarish tarifflarining listini sahifalab olish")
    @GetMapping
    ApiResult<CustomPage<PayBackTariffDto>> getAll(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size);

    @Operation(summary = "Bitta pul qaytarish tariffini olish")
    @GetMapping("/{payBackTariffId}")
    ApiResult<PayBackTariffDto> getById(@PathVariable UUID payBackTariffId);

    @Operation(summary = "Pul qaytarish tariffini qo'shish")
    @PostMapping
    ApiResult<PayBackTariffDto> save(@RequestBody @Valid PayBackTariffReqDto payBackTariffReqDto);

    @Operation(summary = "Pul qaytarish tariffini edit qilish")
    @PutMapping("/{payBackTariffId}")
    ApiResult<PayBackTariffDto> edit(@RequestBody @Valid PayBackTariffReqDto payBackTariffReqDto, @PathVariable UUID payBackTariffId);

    @Operation(summary = "Pul qaytarish tariffini o'chirish")
    @DeleteMapping("/{payBackTariffId}")
    ApiResult<?> delete(@PathVariable UUID payBackTariffId);

}
