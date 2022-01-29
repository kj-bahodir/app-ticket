package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.EventSessionReqDto;
import ai.ecma.appticket.payload.EventSessionResDto;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static ai.ecma.appticket.utils.AppConstant.DEFAULT_PAGE_NUMBER;
import static ai.ecma.appticket.utils.AppConstant.DEFAULT_PAGE_SIZE;

@RequestMapping(RestConstant.EVENT_SESSION_CONTROLLER)
@Tag(name = "Tadbir seksiyalarning operatsiyalari",description = "Tadbir seksiyasi")
public interface EventSessionController {

    @Operation(summary = "TadbirLar seksiyalarining listini sahifalab olish")
    @GetMapping
     ApiResult<CustomPage<EventSessionResDto>> getAll(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size);

    @Operation(summary = "Bitta Tadbir seksiyasini olish")
    @GetMapping("/byId/{id}")
    ApiResult<EventSessionResDto> getOne(@PathVariable UUID id);

    @Operation(summary = "Tadbir id orqali tadbir seksiyalarining listini olish")
    @GetMapping("/byEventId/{eventId}")
    ApiResult<List<EventSessionResDto>> getByEventId(@PathVariable UUID eventId);

    @Operation(summary = "Tadbir seksiyasini qo'shish")
    @PostMapping
    ApiResult<EventSessionResDto> add(@RequestBody @Valid EventSessionReqDto eventSessionReqDto);

    @Operation(summary = "Tadbir seksiyasini edit qilish")
    @PutMapping("/{id}")
    ApiResult<EventSessionResDto> edit(@RequestBody @Valid EventSessionReqDto eventSessionReqDto, @PathVariable UUID id);

    @Operation(summary = "Tadbir seksiyasini o'chirish")
    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);

}

