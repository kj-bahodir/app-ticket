package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.AddressResDto;
import ai.ecma.appticket.payload.AddressReqDto;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.utils.RestConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(RestConstant.ADDRESS_CONTROLLER)
@Tag(name = "Address operatsiyalari",description = "Address")
public interface AddressController {

    @Operation(summary = "Addresslarning listini olish")
    @GetMapping("/get-all")
    ApiResult<List<AddressResDto>> getAll();

    @Operation(summary = "Bitta addressni o'zini olish")
    @GetMapping("/get/{id}")
    ApiResult<AddressResDto> getAddress(@PathVariable UUID id);

    @Operation(summary = "Address qo'shish")
    @PostMapping("/add")
    ApiResult<AddressResDto> add(@RequestBody @Valid AddressReqDto addressReqDto);

    @Operation(summary = "Addressni edit qilish")
    @PutMapping("/edit/{id}")
    ApiResult<AddressResDto> edit(@RequestBody @Valid AddressReqDto addressReqDto, @PathVariable UUID id);

    @Operation(summary = "Addressni o'chirish")
    @DeleteMapping("/delete/{id}")
    ApiResult<?> delete(@PathVariable UUID id);
}
