package ai.ecma.appticket.service;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.BronTariffReqDto;
import ai.ecma.appticket.payload.BronTariffResDto;

import java.util.UUID;

public interface BronTariffService {

    ApiResult<BronTariffResDto> add(BronTariffReqDto bronTariffReqDto);

    ApiResult<BronTariffResDto> getOne(UUID id);

    ApiResult<?> delete(UUID id);

}
