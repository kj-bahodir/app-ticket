package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.SpecializationDto;
import ai.ecma.appticket.payload.SpecializationReqDto;
import ai.ecma.appticket.utils.AppConstant;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static ai.ecma.appticket.utils.AppConstant.DEFAULT_PAGE_NUMBER;
import static ai.ecma.appticket.utils.AppConstant.DEFAULT_PAGE_SIZE;

@RequestMapping(RestConstant.SPECIALIZATION_CONTROLLER)
@Tag(name = "Mutahasislik operatsiyalari",description = "Mutahasislik")
public interface SpecializationController {

    @Operation(summary = "Mutahasislikalarning listini sahifalab olish")
    @GetMapping
    ApiResult<CustomPage<SpecializationDto>> getAll(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size);

    @Operation(summary = "Bitta mutahasislik olish")
    @GetMapping("/{uuid}")
    ApiResult<SpecializationDto> getOne(@PathVariable UUID uuid);

    @Operation(summary = "Mutahasislikni qo'shish")
    @PostMapping
    ApiResult<SpecializationDto> add(@RequestBody @Valid SpecializationReqDto specializationReqDto);

    @Operation(summary = "Mutahasislikni edit qilish")
    @PutMapping("/{uuid}")
    ApiResult<SpecializationDto> edit(@RequestBody @Valid SpecializationReqDto specializationReqDto, @PathVariable UUID uuid);

    @Operation(summary = "Mutahasislikni o'chirish")
    @DeleteMapping("/{uuid}")
    ApiResult<?> delete(@RequestBody @Valid UUID uuid);
}
