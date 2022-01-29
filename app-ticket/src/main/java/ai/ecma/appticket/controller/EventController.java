package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.EventReqDto;
import ai.ecma.appticket.payload.EventResDTO;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static ai.ecma.appticket.utils.AppConstant.DEFAULT_PAGE_NUMBER;
import static ai.ecma.appticket.utils.AppConstant.DEFAULT_PAGE_SIZE;

@RequestMapping(RestConstant.EVENT_CONTROLLER)
@Tag(name = "Tadbir(event) operatsiyalari",description = "Tadbir(event)")
public interface EventController {

    @Operation(summary = "Tadbirlarning listini sahifalab olish")
    @GetMapping
    ApiResult<CustomPage<EventResDTO>> getAll(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size);

    @Operation(summary = "Bitta tadbirni olish")
    @GetMapping("/{id}")
    ApiResult<EventResDTO> getOne(@PathVariable UUID id);

    @Operation(summary = "Tadbir qo'shish")
    @PostMapping
    ApiResult<EventResDTO> add(@RequestBody @Valid EventReqDto eventReqDto);

    @Operation(summary = "Tadbirni edit qilish")
    @PutMapping("/{id}")
    ApiResult<EventResDTO> edit(@RequestBody @Valid EventReqDto eventReqDto, @PathVariable UUID id);

    @Operation(summary = "Tadbirni o'chirish")
    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);
}
