package ai.ecma.appticket.service;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.PayTypeResDto;

import java.util.List;
import java.util.UUID;

public interface PayTypeService {
    ApiResult<List<PayTypeResDto>> getAll();

    ApiResult<PayTypeResDto> get(UUID id);

    ApiResult<PayTypeResDto> create(PayTypeResDto payTypeResDto);

    ApiResult<PayTypeResDto> edit(PayTypeResDto payTypeResDto, UUID id);

    ApiResult<?> delete(UUID id);
}
