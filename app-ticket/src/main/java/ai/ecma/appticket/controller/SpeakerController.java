package ai.ecma.appticket.controller;

import ai.ecma.appticket.entity.User;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.SpeakerResDto;
import ai.ecma.appticket.payload.SpeakerReqDto;
import ai.ecma.appticket.security.CurrentUser;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static ai.ecma.appticket.utils.AppConstant.DEFAULT_PAGE_NUMBER;
import static ai.ecma.appticket.utils.AppConstant.DEFAULT_PAGE_SIZE;

@RequestMapping(RestConstant.SPEAKER_CONTROLLER)
@Tag(name = " Speaker operatsiyalari",description = "Speaker")
public interface SpeakerController {

    @Operation(summary = "Speakerlarning listini sahifalab olish")
    @GetMapping
    ApiResult<CustomPage<SpeakerResDto>> getAll(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size);

    @Operation(summary = "Bitta Speakerni olish")
    @GetMapping("/{id}")
    ApiResult<SpeakerResDto> getOne(@PathVariable UUID id);

    @Operation(summary = "Speaker qo'shish")
    @PostMapping
    ApiResult<SpeakerResDto> add(@RequestBody @Valid SpeakerReqDto speakerDto);

    @Operation(summary = "Speakerni edit qilish")
    @PutMapping("/{id}")
    ApiResult<SpeakerResDto> edit(@RequestBody @Valid SpeakerReqDto speakerDto, @PathVariable UUID id);

    @Operation(summary = "Speakerni delete qilish")
    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);
}
