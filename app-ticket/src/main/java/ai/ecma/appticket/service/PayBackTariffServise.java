package ai.ecma.appticket.service;

import ai.ecma.appticket.payload.ApiResult;
import ai.ecma.appticket.payload.CustomPage;
import ai.ecma.appticket.payload.PayBackTariffDto;
import ai.ecma.appticket.payload.PayBackTariffReqDto;

import java.util.UUID;

public interface PayBackTariffServise {

    ApiResult<CustomPage<PayBackTariffDto>> getAll(int page,int size);

    ApiResult<PayBackTariffDto> getById(UUID payBackTariffId);

    ApiResult<PayBackTariffDto> save(PayBackTariffReqDto payBackTariffReqDto);

    ApiResult<PayBackTariffDto> edit(PayBackTariffReqDto payBackTariffReqDto, UUID payBackTariffId);

    ApiResult<?> delete(UUID payBackTariffId);
}
