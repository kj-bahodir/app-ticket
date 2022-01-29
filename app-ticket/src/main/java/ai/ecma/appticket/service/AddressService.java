package ai.ecma.appticket.service;

import ai.ecma.appticket.payload.AddressResDto;
import ai.ecma.appticket.payload.AddressReqDto;
import ai.ecma.appticket.payload.ApiResult;

import java.util.List;
import java.util.UUID;

public interface AddressService {
    ApiResult<List<AddressResDto>> getAll();

    ApiResult<AddressResDto> getAddress(UUID id);

    ApiResult<AddressResDto> add(AddressReqDto addressReqDto);

    ApiResult<AddressResDto> edit(AddressReqDto addressReqDto, UUID id);

    ApiResult<?> delete(UUID id);
}
