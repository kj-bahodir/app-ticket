package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.BronTariffReqDto;
import ai.ecma.appticket.payload.BronTariffResDto;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping(RestConstant.BRON_TARIFF_CONTROLLER)
@Tag(name = "Bron tariff operatsiyalari",description = "Bron tariff")
public interface BronTariffController {

    @PostMapping
    @Operation(summary = "Bron tarif qo'shish")
    public ApiResult<BronTariffResDto> add(@RequestBody @Valid BronTariffReqDto bronTariffReqDto);

    @Operation(summary = "Bitta bron tarifni olish")
    @GetMapping("/{id}")
    public ApiResult<BronTariffResDto> getOne(@PathVariable UUID id);

    @Operation(summary = "Bron tarifni o'chirish")
    @DeleteMapping("/{id}")
    public ApiResult<?> delete(@PathVariable UUID id);

}
