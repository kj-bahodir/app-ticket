package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.EventSpeakerReqDto;
import ai.ecma.appticket.payload.EventSpeakerResDto;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(RestConstant.EVENT_SPEAKER_CONTROLLER)
@Tag(name = "Tadbir Speakeri operatsiyalari",description = "Tadbir speakeri")
public interface EventSpeakerController {

    @Operation(summary = "Tadbir speakerlarini listini olish")
    @GetMapping
    ApiResult<List<EventSpeakerResDto>> getAll();

    @Operation(summary = "Bitta Tadbir speakirini olish")
    @GetMapping("/{id}")
    ApiResult<EventSpeakerResDto> getOne(@PathVariable UUID id);

    @Operation(summary = "Tadbir speakerini qo'shish")
    @PostMapping
    ApiResult<EventSpeakerResDto> add(@RequestBody @Valid EventSpeakerReqDto eventSpeakerReqDto);

    @Operation(summary = "Tadbir speakerini edit qilish")
    @PutMapping("/{id}")
    ApiResult<EventSpeakerResDto> edit(@RequestBody @Valid EventSpeakerReqDto eventSpeakerReqDto,
                                       @PathVariable UUID id);

    @Operation(summary = "Tadbir speakerini o'chirish")
    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);
}
