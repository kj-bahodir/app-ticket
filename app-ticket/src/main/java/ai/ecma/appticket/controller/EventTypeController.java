package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.EventTypeResDto;
import ai.ecma.appticket.payload.EventTypeReqDto;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RequestMapping(RestConstant.EVENT_TYPE_CONTROLLER)
@Tag(name = "Tadbir turi(Biznes) operatsiyalari",description = "Tadbir turi")
public interface EventTypeController {

    @Operation(summary = "Tadbir turlarining listini olish")
    @GetMapping
    ApiResult<List<EventTypeResDto>> getAll();

    @Operation(summary = "Bitta tadbir turini olish")
    @GetMapping("/{id}")
    ApiResult<EventTypeResDto> get(@PathVariable UUID id);

    @Operation(summary = "Tadbir turini qo'shish")
    @PostMapping
    ApiResult<EventTypeResDto> add(@RequestBody @Valid EventTypeReqDto eventTypeReqDto);

    @Operation(summary = "Tadbir turini edit qilish")
    @PutMapping("/{id}")
    ApiResult<EventTypeResDto> edit(@RequestBody @Valid EventTypeReqDto eventTypeReqDto,
                                    @PathVariable UUID id);
    @Operation(summary = "Tadbir turini o'chirish")
    @DeleteMapping
    ApiResult<?> delete(@PathVariable UUID id);
}
