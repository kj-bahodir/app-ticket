package ai.ecma.appticket.controller;

import ai.ecma.appticket.payload.AddressResDto;
import ai.ecma.appticket.payload.AddressReqDto;
import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AddressControllerImpl implements AddressController{

    private final AddressService addressService;

    @Override
    public ApiResult<List<AddressResDto>> getAll() {
        return addressService.getAll();
    }

    @Override
    public ApiResult<AddressResDto> getAddress(UUID id) {
        return addressService.getAddress(id);
    }


    @Override
    public ApiResult<AddressResDto> add(AddressReqDto addressReqDto) {
        return addressService.add(addressReqDto);
    }

    @Override
    public ApiResult<AddressResDto> edit(AddressReqDto addressReqDto, UUID id) {
        return addressService.edit(addressReqDto,id);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return addressService.delete(id);
    }
}
